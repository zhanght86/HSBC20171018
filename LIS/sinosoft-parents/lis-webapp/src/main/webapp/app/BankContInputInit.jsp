<%
//程序名称：ContInit.jsp
//程序功能：
//创建日期：2005-05-12 08:49:52
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.service.*"%>
<%!
	ExeSQL exeSQL = new ExeSQL();
	SSRS   ssrs   = null; 
	String getEncodedResult(String sql)
	{
		return exeSQL.getEncodedResult(sql);
	}
	SSRS execQuery(String sql)
	{
			ssrs = exeSQL.execSQL(sql);
			return ssrs;
	}	
%>

<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
var loadFlag=<%=tLoadFlag%>;

//初始化输入框
function initInpBox()
{ 
}

// 下拉框的初始化
function initSelBox()
{
}

function initForm()
{
	initForm_old();
	getLostInfo();
}

//表单初始化
function initForm_old()
{
  try
  {  
    //Q:scantype是判断是否有扫描件，用于定制随动
    if(scantype=="scan")
    {
      setFocus();
    }
    else
    {
    fm.insuworkimpart.disabled=true;
    fm.insuhealthmpart.disabled=true;
    }     
    //初始化业务员告知
    initAgentImpartGrid();    
    //初始化投保人告知信息
    initImpartGrid();
    //初始化工作流任务节点
    initMissionID();
    //初始化多业务员列表
    initMultiAgentGrid();   
    //判断是否是有扫描件 
    /**********************
     *ScanFlag=0--有扫描　
     *ScanFlag=1--无扫描　
     **********************
     */
     
    if(this.ScanFlag == "0")
    {
      //根据需求PrtNo中存投保单号，故将投保单号字段赋值
      document.all('PrtNo').value = prtNo;
      document.all('PrtNo2').value = prtNo;
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true; 
    }
   
    if(this.ScanFlag == "1")
    {
      document.all('PrtNo').value = prtNo;
      document.all('PrtNo2').value = prtNo;
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true;
    }

//有些信息没有查出来,故用此函数来补充.
//     getLostInfo();
    
    //按照不同的LoadFlag进行不同的处理
    //得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/
     //Q: 7,8,9,10没有分支处理 25不知道是什么
     //7,8,9
//  document.all('LoadFlag').value = this.LoadFlag;
//  alert(fm.LoadFlag.value);
    //新单录入
    if(this.loadFlag=="1")
    {
    	//Q:新单录入为何要用PolNo查询？ hl 20050505
      //detailInit(mSwitch.getVar( "PolNo" ));
        fm.SellType.value="02"; //销售方式：银代录单默认为“02－柜面”
		fm.AppntIDType.value="0";
		fm.AppntNativePlace.value="CHN";
		fm.NativePlace.value="CHN";
		document.getElementById("divButton").style.display="";
		document.getElementById("divAddDelButton").style.display = "";
		document.getElementById("divInputContButton").style.display = "";  
		detailInit(prtNo);
		getImpartInitInfo();
		//判断记事本中的记录数量
		checkNotePad(prtNo,loadFlag);
		
      	return;
    }
    
    //问题件修改
    if(this.loadFlag=="3")
    {
	　    //详细信息初始化
	　   detailInit(prtNo); 
	　   AppntChkNew();
		document.getElementById("divButton").style.display="";
		document.getElementById("divInputContButton").style.display = "none";
		document.getElementById("divApproveContButton").style.display = "none";
		document.getElementById("divProblemModifyContButton").style.display = "";
		document.getElementById("operateButton").style.display="";
		document.getElementById("divAddDelButton").style.display="";
		AppntChkNew();
		//判断记事本中的记录数量
		checkNotePad(prtNo,loadFlag);
		if(scantype=="scan")
	    {
	      setFocus();
	    }
	    else
	    {
	    fm.insuworkimpart.disabled=true;
	    fm.insuhealthmpart.disabled=true;
	    }  
		return;  
    }
    
    //新单复核  
    if(this.LoadFlag=="5")
    {    
		document.getElementById("divButton").style.display="none";
		document.getElementById("divInputContButton").style.display = "none";
		document.getElementById("divApproveModifyContButton").style.display = "none";
		document.getElementById("divApproveContButton").style.display = "";
　    //详细信息初始化
		<%--
		com.sinosoft.lis.tb.ContInfoQuery info = new com.sinosoft.lis.tb.ContInfoQuery(request.getParameter("prtNo"));
		%>
		<%=info.getScriptString() --%>
        detailInitServer();
        //判断是否有重复客户
　      AppntChkNew();
		  //判断记事本中的记录数量
		  checkNotePad(prtNo,loadFlag);

      //按钮可选判断
      ctrlButtonDisabled(prtNo,LoadFlag);
		  
        return;  
    }
    
    //投保信息查询核保
    if (this.loadFlag == "6")
    {
      detailInit(prtNo); 
      document.getElementById("divButton").style.display="none";
      document.getElementById("riskbutton3").style.display="";  //进入险种界面
      //查询投保人详细信息                 
  	  return;
    }

    //保全新保加人
    if(this.loadFlag == "7")
    {
      //判断是否有重复客户
　    AppntChkNew();
      return;  
    }    
    
    //保全新增附加险
    if(this.loadFlag == "8")
    {
      //判断是否有重复客户
　    AppntChkNew();
      return;  
    }    
    
    //无名单补名单
    if(this.loadFlag == "9")
    {
      //判断是否有重复客户
　    AppntChkNew();
      return;  
    }    
    //无名单补名单
    if(this.loadFlag == "10")
    {
      //判断是否有重复客户
　    AppntChkNew();
      return;  
    }    
    
    //人工核保修改投保单
    if(this.loadFlag=="25")
    {      
　      //详细信息初始化
		detailInit(prtNo);    
		document.getElementById("divAddDelButton").style.display = "";
		document.getElementById("divchangplan").style.display = "";
		document.getElementById("divButton").style.display="";
        return;  
    }
    
    //随动定制
    if(this.loadFlag == "99")
    {
      document.getElementById("divInputContButton").style.display="none";
      document.getElementById("autoMoveButton").style.display="";    
      //判断是否有重复客户
　    AppntChkNew();
      return;  
    }       
  }
  catch(ex)
  {}
}

function initInsured()
{
	return ;
	<%--
	com.sinosoft.lis.tb.InsuredInfoQuery info1 = new com.sinosoft.lis.tb.InsuredInfoQuery(request.getParameter("prtNo"));
	%>
	<%=info1.getScriptString() --%>
}

// 告知信息列表的初始化
function initImpartGrid() 
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
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
			iArray[1][4]="impver_appnt_bank";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="AppntImpart";
      //iArray[1][11]="0|^01|财务及其他告知^03|健康告知";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

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

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";         		//列名
//      iArray[5][1]="90px";            		//列宽
//      iArray[5][2]=90;            			//列最大值
//      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";         		//列名
//      iArray[6][1]="90px";            		//列宽
//      iArray[6][2]=90;            			//列最大值
//      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 0;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}
function initMultiAgentGrid()
{
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务员代码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="queryAgentGrid";  　    //双击调用查询业务员的函数          

      iArray[2]=new Array();
      iArray[2][0]="业务员姓名";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="所属机构";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="营业部、组";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="佣金比例";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="AgentGroup";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许


      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //这些属性必须在loadMulLine前
      MultiAgentGrid.mulLineCount = 1;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
	
}
// 告知信息列表的初始化
function initAgentImpartGrid() 
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
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
			iArray[1][4]="impver_agent_bank";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="AgentImpart";
      //iArray[1][11]="0|^05|业务员告知";
      iArray[1][18]=300;
	  	iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

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

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";         		//列名
//      iArray[5][1]="90px";            		//列宽
//      iArray[5][2]=90;            			//列最大值
//      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";         		//列名
//      iArray[6][1]="90px";            		//列宽
//      iArray[6][2]=90;            			//列最大值
//      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      //这些属性必须在loadMulLine前
      AgentImpartGrid.mulLineCount = 0;
      //AgentImpartGrid.hiddenPlus = 1;
      //AgentImpartGrid.hiddenSubtraction = 1;
      AgentImpartGrid.canChk = 0;   
      AgentImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      AgentImpartGrid.loadMulLine(iArray);  

    }
    catch(ex) 
    {
      alert(ex);
    }
}



/*****************************************************
 *   将BankContInsuredInit.jsp中的内容加入到本页中
 *   
 *****************************************************
 */


 
function initInpBox2()
{ 
  try
  { 
  	//SelPolNo当前选中险种的保单号
  	fm.SelPolNo.value='';
  	try{mSwitch.deleteVar("PolNo");}catch(ex){};
  	fm.InsuredNo.value='';
  }
  catch(ex)
  {
    alert("在ContInsuredInit2.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox2()
{  
  try                 
  {
/*********  hanlin 20050416  不知道是干什么用的。
   if(checktype=="1")
   {
     param="121";
     fm.pagename.value="121";     
   }
   if(checktype=="2")
   {
     param="22";
     fm.pagename.value="22";     
   }   
*********/
  }
  catch(ex)
  {
    alert("在ContInsuredInit2.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm2()
{
//  try
//  { 
//    alert("1");
    initInpBox2();
//     alert("2");
    initSelBox2();  
//     alert("3");  
    initInsuredGrid();
//     alert("4"); 
    initImpartGrid2();
//     alert("5");
    //initImpartDetailGrid2();
    initPolGrid();
//     alert("6");
    // initGrpInfo();
    //getContInfo(); 
    //查询被保人信息。
    getInsuredInfo();  
  //   alert("7");
    document.getElementById("DivGrpNoname").style.display="none";
//     alert("8");
    //判断是否是家庭单，如果不是则不显示被保人列表？
    //alert(document.all('FamilyType').value);
   /********  
    if(document.all('FamilyType').value==''||document.all('FamilyType').value==null||document.all('FamilyType').value=='0'||document.all('FamilyType').value=='false'){  
      document.all('FamilyType').value='0';
      divTempFeeInput.style.display="none";
      getProposalInsuredInfo();          //获得个人单信息，填写被保人表
    }
  *********/
//  }
//  catch(re)
//  {
//    alert("ContInsuredInit2.jsp-->InitForm函数中发生异常:初始化界面错误!");
//  }
  
  //hanlin 20050416
  ContType = fm.ContType.value;
  if(fm.ContType.value=="2")
  {
  	initContPlanCode();
	initExecuteCom(); 
  }
  
  //按照不同的LoadFlag进行不同的处理
  //得到界面的调用位置,默认为1,表示个人保单直接录入.
  /**********************************************
   *
   *LoadFlag=1  -- 个人投保单直接录入
   *LoadFlag=2  -- 集体下个人投保单录入
   *LoadFlag=3  -- 个人投保单明细查询
   *LoadFlag=4  -- 集体下个人投保单明细查询
   *LoadFlag=5  -- 复核
   *LoadFlag=6  -- 查询
   *LoadFlag=7  -- 保全新保加人
   *LoadFlag=8  -- 保全新增附加险
   *LoadFlag=9  -- 无名单补名单
   *LoadFlag=10 -- 浮动费率
   *LoadFlag=13 -- 集体下投保单复核修改
   *LoadFlag=16 -- 集体下投保单查询
   *LoadFlag=23 -- 团单核保承保计划变更
   *LoadFlag=25 -- 个单核保承保计划变更
   *LoadFlag=99 -- 随动定制
   *
   ************************************************/
  showCodeName();  //代码汉化


  //个险新单录入
  if(LoadFlag=="1")
  { 
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("divFamilyType").style.display="";
    //divPrtNo.style.display="";
    document.getElementById("divSamePerson").style.display="";
    document.getElementById("DivGrpNoname").style.display="none";  
    fm.PolTypeFlag.value='0';
    return;     
  }
  
  //集体下个人投保单录入
  if(LoadFlag=="2")
  { 
    fm.InsuredSequencename.value="被保险人资料";   
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divGrpInputContButton").style.display="";
    document.getElementById("divLCInsuredPerson").style.display="none";   
    document.getElementById("DivRelation").style.display="none";
    document.getElementById("DivAddress").style.display="none";
    document.getElementById("DivClaim").style.display="none";       
    return;     
  }
  
  //个人投保单明细查询
  if(LoadFlag=="3")
  {
    document.getElementById("divTempFeeInput").style.display="";
    //getInsuredInfo(); 前面已经运行过
    InsuredChkNew();
    return;     
  }
  
  //集体下个人投保单明细查询
  if(LoadFlag=="4")
  {
    document.getElementById("divFamilyType").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divApproveContButton").style.display="";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("divCheckInsuredButton").style.display="none";        
    //getInsuredInfo();  前面已经运行过
    return;     
  }
  
  //复核
  if(LoadFlag=="5")
  {
    document.getElementById("divTempFeeInput").style.display="";
    //getInsuredInfo();  前面已经运行过
    document.getElementById("divFamilyType").style.display="";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("butBack").style.display="none";
    document.getElementById("divSamePerson").style.display="none";   
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divApproveContButton").style.display="";
    document.getElementById("divLCInsuredPerson").style.display="none";
//    InsuredChkNew();
    //alert("复核1")
    return;     
  }

  //查询
  if(LoadFlag=="6")
  {
  	//被保人列表
    document.getElementById("divTempFeeInput").style.display="";
    //divPrtNo.style.display="";    
    //录单按钮
    document.getElementById("divInputContButton").style.display="none";
    
    //divQueryButton.style.display="";
    //divFamilyType.style.display="";
    //添加、删除、修改被保人
    document.getElementById("divAddDelButton").style.display="none";
    //被保人信息“查询”按钮
    document.getElementById("InsuredButBack").style.display="none";
    //“如投保人为被保险人本人，可免填本栏”checkbox
    document.getElementById("divSamePerson").style.display="none";
    document.getElementById("divInputQuery").style.display="";
    return;     
  }

  //保全新保加人
  if(LoadFlag=="7")
  {
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divGrpInputContButton").style.display="";
    document.getElementById("divLCInsuredPerson").style.display="none";   
    fm.InsuredSequencename.value="被保险人资料"; 
    return;     
  }

  //保全新增附加险
  if(LoadFlag=="8")
  {
    return;	
  }

  //无名单补名单
  if(LoadFlag=="9")
  {
    return;	
  }

  //浮动费率
  if(LoadFlag=="10")
  {
    return;	
  }

  //集体下投保单复核修改
  if(LoadFlag=="13")
  { 
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divSamePerson").style.display="none";
    //divPrtNo.style.display="none";
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divLCInsuredPerson").style.display="none"; 
    document.getElementById("divApproveModifyContButton").style.display="";
    //getInsuredInfo();  前面已经运行过
    return;     
  }

  //集体下投保单查询
  if(LoadFlag=="16")
  {
    fm.InsuredSequencename.value="被保险人资料";     
    document.getElementById("divTempFeeInput").style.display="none";
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divQueryButton").style.display="";
    document.getElementById("divFamilyType").style.display="none";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("butBack").style.display="none";
    document.getElementById("divSamePerson").style.display="none"; 
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("DivRelation").style.display="none";
    document.getElementById("DivAddress").style.display="none";
    document.getElementById("DivClaim").style.display="none";    
    return;     
  }

  //个单核保承保计划变更
  if(LoadFlag=="25")
  {
    document.getElementById("divTempFeeInput").style.display="";
    //getInsuredInfo(); //前面已经运行过
    document.getElementById("divFamilyType").style.display="";
    document.getElementById("divAddDelButton").style.display="none";
    document.getElementById("butBack").style.display="none";
    document.getElementById("divSamePerson").style.display="none";   
    document.getElementById("divInputContButton").style.display="none";
    document.getElementById("divApproveContButton").style.display="none";
    document.getElementById("divLCInsuredPerson").style.display="none";
    document.getElementById("divchangplan").style.display="";
    return;     
  }
  
  //随动定制
  if(this.LoadFlag == "99")
  {
  	if(checktype=="1")
  	{
      document.getElementById("divAddDelButton").style.display="none";
      document.getElementById("divInputContButton").style.display="none";
      document.getElementById("autoMoveButton").style.display="";    
      return;     
    }
    if(checktype=="2")
    {
　　  document.getElementById("divAddDelButton").style.display="none";
      document.getElementById("divInputContButton").style.display="none";
      document.getElementById("autoMoveButton").style.display="";    
      return;     
    }
    return;
  }

}


// 被保人信息列表的初始化
function initInsuredGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号码";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别";      	   		//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=2;              	//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="Sex"; 

      iArray[4]=new Array();
      iArray[4][0]="出生日期";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="与第一被保险人关系";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2; 
      iArray[5][4]="Relation";              	        //是否引用代码:null||""为不引用
      iArray[5][9]="与主被保险人关系|code:Relation&NOTNULL";
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="客户内部号";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=2; 
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人";      
	  iArray[6][19]=1;

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      InsuredGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 被保人告知信息列表的初始化
function initImpartGrid2() 
{                               
    var iArray = new Array();     
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

//      iArray[1]=new Array();
//      iArray[1][0]="告知版别";         		//列名
//      iArray[1][1]="60px";            		//列宽
//      iArray[1][2]=60;            			//列最大值
//      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//			iArray[1][4]="impver_insu_bank";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^13|健康告知^14|职业告知";
//      iArray[1][18]=300;
//      iArray[1][19]=1;

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ImpartVer1";                     //新版投保单对应告知
      iArray[1][5]="1|6";
      iArray[1][6]="0|0";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][15]="1";
      iArray[1][16]="1 and code not in (#101#,#102#,#103#,#105#,#106#)";
      iArray[1][18]=300;
      iArray[1][19]=1;


      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

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

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";         		//列名
//      iArray[5][1]="90px";            		//列宽
//      iArray[5][2]=90;            			//列最大值
//      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";         		//列名
//      iArray[6][1]="90px";            		//列宽
//      iArray[6][2]=90;            			//列最大值
//      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      ImpartInsuredGrid = new MulLineEnter( "fm" , "ImpartInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartInsuredGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}
// 告知明细信息列表的初始化
function initImpartDetailGrid2() 
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
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="impver_insu_bank";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="InsuredImpartDetail";
      //iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2";
      iArray[2][6]="0";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="内容";         		//列名
      iArray[3][1]="450px";            		//列宽
      iArray[3][2]=2000;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许


/*******************************
      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="开始时间";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="开始时间|date";

      iArray[5]=new Array();
      iArray[5][0]="结束时间";         		//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][9]="结束时间|date";
      
      iArray[6]=new Array();
      iArray[6][0]="证明人";         		//列名
      iArray[6][1]="90px";            		//列宽
      iArray[6][2]=90;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="目前情况";         		//列名
      iArray[7][1]="90px";            		//列宽
      iArray[7][2]=90;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[8]=new Array();
      iArray[8][0]="能否证明";         		//列名
      iArray[8][1]="90px";            		//列宽
      iArray[8][2]=90;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][4]="yesno";
*******************************/    
 
      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartDetailGrid.mulLineCount = 0;   
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}
//被保人险种信息列表初始化
function initPolGrid()
{
    var iArray = new Array(); 
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		        //列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保险单险种号码";         		//列名
      iArray[1][1]="0px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="40px";            		        //列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[3]=new Array();
      iArray[3][0]="险种名称";         		//列名
      iArray[3][1]="150px";            		        //列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[4]=new Array();
      iArray[4][0]="合计保费(元)";         		//列名
      iArray[4][1]="40px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="保额(元)";         		//列名
      iArray[5][1]="40px";            		        //列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;            			//是否允许输入,1表示允许，0表示不允许
      
  		iArray[6]=new Array();
      iArray[6][0]="币种";         		//列名
      iArray[6][1]="40px";            		        //列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[6][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[6][9]="币种|code:Currency";
          
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}


function getContInfo()
{
    try { document.all( 'ContNo' ).value = mSwitch.getVar( "ContNo" ); if(document.all( 'ContNo' ).value=="false"){document.all( 'ContNo' ).value="";} } catch(ex) { };
    try { document.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
    try { document.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalContNo" ); } catch(ex) { };   
    try { document.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { document.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    try { document.all( 'PolTypeFlag' ).value = mSwitch.getVar( "PolType" );if(document.all( 'PolTypeFlag' ).value=="false"){document.all( 'PolTypeFlag' ).value="0";} } catch(ex) {};
    try { document.all( 'InsuredPeoples' ).value = mSwitch.getVar( "InsuredPeoples" );if(document.all( 'InsuredPeoples' ).value=="false"){document.all( 'InsuredPeoples' ).value="";} } catch(ex) {};
}

function initContPlanCode() 
{
	 //alert(mSwitch.getVar( "ProposalGrpContNo" ));
	 document.all("ContPlanCode").CodeData=getContPlanCode(mSwitch.getVar( "ProposalGrpContNo" ));	
}

function initExecuteCom()
{
	 document.all("ExecuteCom").CodeData=getExecuteCom(mSwitch.getVar( "ProposalGrpContNo" ));	
}

function initGrpInfo()
{
	//fm.PrtNo.value=mSwitch.getVar('PrtNo');
	//fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	//fm.ManageCom.value=mSwitch.getVar('ManageCom');
	//fm.AgentCode.value=mSwitch.getVar('AgentCode');
	//fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
//	alert("sfasdf");
	//fm.GrpName.value=mSwitch.getVar('GrpName');
	//fm.CValiDate.value=mSwitch.getVar('CValiDate');
	//fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}




/*****************************************************
 *   结束
 *   
 *****************************************************
 */

/*********************************************************************
 *  投保单信息初始化函数。以loadFlag标志作为分支
 *  参数  ：  投保单印刷号
 *  返回值：  无
 *********************************************************************
 */
function detailInitServer(){
//alert("start : detailInitServer");
<%
	String sql,contNo=null ,customerNo=null ,addressNo=null ;
	
	String PrtNo=request.getParameter("prtNo");
	String ContNo=request.getParameter("ContNo");
	if (null == PrtNo || "".equals(PrtNo))
	{}
	else
	{
%>

		var PrtNo = "<%=PrtNo%>";
		var ContNo = "<%=ContNo%>";

    if(PrtNo==null) return;

    <%
       sql = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
               + "where (a.TempFeeNo)=(b.TempFeeNo) and a.PayMode='7' and b.TempFeeType='1' "
               + "and b.OtherNo='"+ PrtNo +"'"; 
  TransferData sTransferData=new TransferData();
  sTransferData.setNameAndValue("SQL", sql);
  VData sVData = new VData();
  sVData.add(sTransferData);
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  String sOverDueFlag = "";
  SSRS MRSSRS = new SSRS();
  if(tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI"))
  {
	  VData responseVData = tBusinessDelegate.getResult();
	  MRSSRS = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
  }
    %>
         arrResult = decodeResult("<%=MRSSRS.encode()%>");
      
    //alert(arrResult);
    if(arrResult==null)
    {}
    else
    {
    //alert("aaaaa");
      displayFirstAccount();
      //如果首期交费方式为银行转账，续期同样为银行转账
      document.all('PayMode').value="";
    }


//    arrResult=easyExecSql("select * from LCCont where PrtNo='"+PrtNo+"'",1,0);
		<% 
		   sql = "select * from LCCont where PrtNo='"+PrtNo+"'";
		   TransferData sTransferData2=new TransferData();
		   sTransferData2.setNameAndValue("SQL", sql);
		   VData sVData2 = new VData();
		   sVData2.add(sTransferData2);
		   BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS2 = new SSRS();
		   if(tBusinessDelegate2.submitData(sVData2, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate2.getResult();
		 	  MRSSRS2 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
//		execQuery(sql);
			ssrs = MRSSRS2 ;
			
			if(ssrs.getMaxRow()<1)
			{}
			else
			{
				customerNo = ssrs.GetText(1,20);
		%>
		arrResult = decodeResult("<%=ssrs.encode()%>");

    if(arrResult==null){
      alert("未得到投保单信息");
      return;
    }
    else{
      //alert(arrResult);
      displayLCCont();       //显示投保单详细内容
    }


//    arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);
		<% sql="select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + customerNo + "'"; 
		   TransferData sTransferData3=new TransferData();
		   sTransferData3.setNameAndValue("SQL", sql);
		   VData sVData3 = new VData();
		   sVData3.add(sTransferData3);
		   BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS3 = new SSRS();
		   if(tBusinessDelegate3.submitData(sVData3, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate3.getResult();
		 	 MRSSRS3 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
 		%>
		arrResult = decodeResult("<%=MRSSRS3.encode()%>");

    if (arrResult == null) {
      alert("未查到投保人客户层信息");
    }
    else{
      //显示投保人信息
     // alert("start : displayAppnt");
      displayAppnt(arrResult[0]);
    }

    //arrResult=easyExecSql("select * from LCAppnt where PrtNo='"+PrtNo+"'",1,0);
		<% 
			sql = "select * from LCAppnt where PrtNo='"+PrtNo+"'";
		   TransferData sTransferData4=new TransferData();
		   sTransferData4.setNameAndValue("SQL", sql);
		   VData sVData4 = new VData();
		   sVData4.add(sTransferData4);
		   BusinessDelegate tBusinessDelegate4=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS4 = new SSRS();
		   if(tBusinessDelegate4.submitData(sVData4, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate4.getResult();
		 	  MRSSRS4 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
//			execQuery(sql);
			ssrs = MRSSRS4;
			if(ssrs.getMaxRow()<1)
			{}
			else
			{
			contNo = ssrs.GetText(1,2);
			customerNo = ssrs.GetText(1,4);
			addressNo = ssrs.GetText(1,10);
			
		%>
		arrResult = decodeResult("<%=ssrs.encode()%>");
	  //alert(arrResult[0][0]);
    if(arrResult==null){
      alert("未得到投保人保单层信息");
      return;
    }else{
      displayContAppnt();       //显示投保人的详细内容
    }
    getAge();
    var tContNo = arrResult[0][1];
    var tCustomerNo = arrResult[0][3];		// 得到投保人客户号
    var tAddressNo = arrResult[0][9]; 		// 得到投保人地址号


    fm.AppntAddressNo.value=tAddressNo;


    //查询并显示投保人地址详细内容
    //getdetailaddress();
		<% 
			sql = "select b.* from LCAddress b where b.AddressNo='"+addressNo+"' and b.CustomerNo='"+customerNo+"'";
		   TransferData sTransferData5=new TransferData();
		   sTransferData5.setNameAndValue("SQL", sql);
		   VData sVData5 = new VData();
		   sVData5.add(sTransferData5);
		   BusinessDelegate tBusinessDelegate5=BusinessDelegate.getBusinessDelegate();
		   SSRS MRSSRS5 = new SSRS();
		   if(tBusinessDelegate5.submitData(sVData5, "execSQL", "ExeSQLUI"))
		   {
		 	  VData responseVData = tBusinessDelegate5.getResult();
		 	  MRSSRS5 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
		   }
		   ssrs = MRSSRS5;
//		execQuery(sql);		
		%>
		arrResult = decodeResult("<%=ssrs.encode()%>");
    displayDetailAddress(arrResult);

    //alert("zzz");
    //查询投保人告知信息
//    var strSQL0="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//    var strSQL1="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
//		arrResult = easyExecSql(strSQL0,1,0);
//		arrResult1 = easyExecSql(strSQL1,1,0);
	<%
		sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
	   TransferData sTransferData6=new TransferData();
	   sTransferData6.setNameAndValue("SQL", sql);
	   VData sVData6 = new VData();
	   sVData6.add(sTransferData6);
	   BusinessDelegate tBusinessDelegate6=BusinessDelegate.getBusinessDelegate();
	   String MRStr6 = new String();
	   if(tBusinessDelegate6.submitData(sVData6, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate6.getResult();
	 	  MRStr6 = (String)responseVData.getObjectByObjectName("String",0);
	   }
	%>
		arrResult = decodeResult("<%=MRStr6%>");		

	<%
	 sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
	   TransferData sTransferData7=new TransferData();
	   sTransferData7.setNameAndValue("SQL", sql);
	   VData sVData7 = new VData();
	   sVData7.add(sTransferData7);
	   BusinessDelegate tBusinessDelegate7=BusinessDelegate.getBusinessDelegate();
	   String MRStr7 = new String();
	   if(tBusinessDelegate7.submitData(sVData7, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate7.getResult();
	 	  MRStr7 = (String)responseVData.getObjectByObjectName("String",0);
	   }
	%>
		arrResult1 = decodeResult("<%=MRStr7%>");		

    try{document.all('Income0').value= arrResult[0][0];}catch(ex){};
    try{document.all('IncomeWay0').value= arrResult1[0][0];}catch(ex){};

//    var strSQL1="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"' and impartver in ('01','02') and impartcode<>'001'";
//    turnPage.strQueryResult = easyQueryVer3(strSQL1, 1, 0, 1);
	<%
		sql = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"' and impartver in ('01','03') ";
	   TransferData sTransferData8=new TransferData();
	   sTransferData8.setNameAndValue("SQL", sql);
	   VData sVData8 = new VData();
	   sVData8.add(sTransferData8);
	   BusinessDelegate tBusinessDelegate8=BusinessDelegate.getBusinessDelegate();
	   String MRStr8 = new String();
	   if(tBusinessDelegate8.submitData(sVData8, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate8.getResult();
	 	  MRStr8 = (String)responseVData.getObjectByObjectName("String",0);
	   }	
	%>     
	displayML("<%=MRStr8%>",ImpartGrid);
        
    //如果有分支
    if(loadFlag=="5"||loadFlag=="25"){
      //showCodeName();
    }    
    //alert("tContNo=="+tContNo);
         
         
         
//   var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+tCustomerNo+"' and ContNo='"+tContNo+"'";
//   turnPage.queryModal(aSQL, AgentImpartGrid);
	<%
		sql = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='"+customerNo+"' and ContNo='"+contNo+"'";
	   TransferData sTransferData9=new TransferData();
	   sTransferData9.setNameAndValue("SQL", sql);
	   VData sVData9 = new VData();
	   sVData9.add(sTransferData9);
	   BusinessDelegate tBusinessDelegate9=BusinessDelegate.getBusinessDelegate();
	   String MRStr9 = new String();
	   if(tBusinessDelegate9.submitData(sVData9, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate9.getResult();
	 	  MRStr9 = (String)responseVData.getObjectByObjectName("String",0);
	   }	
	%>
	displayML("<%=MRStr9%>",AgentImpartGrid);
    
    
<%}
	}
	}
	%>
}        

function initInsuredServer()
{
	<%	if(contNo!=null&&!"".equals(contNo))
	{
	%>
	//alert("document.all(InsuredNo).value1"+document.all("InsuredNo").value);
	<%
	 sql ="select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+contNo+"'";
	   TransferData sTransferData10=new TransferData();
	   sTransferData10.setNameAndValue("SQL", sql);
	   VData sVData10 = new VData();
	   sVData10.add(sTransferData10);
	   BusinessDelegate tBusinessDelegate10=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS10 = new SSRS();
	   if(tBusinessDelegate10.submitData(sVData10, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate10.getResult();
	 	  MRSSRS10 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS10;
//	execQuery(sql);
	 
	 if(ssrs.getMaxRow()<1)
	 {}
	 else
	 {
	 String insuredNo = ssrs.GetText(1,1);
	%>
	//alert("document.all(InsuredNo).value2"+document.all("InsuredNo").value);
	//displayML("<%=ssrs.encode()%>",InsuredGrid);
	try{document.all(InsuredGrid.findSpanID(0)).all('InsuredGridSel').checked =true;} catch(ex) {}
	//alert("document.all(InsuredNo).value3"+document.all("InsuredNo").value);
   var InsuredNo=InsuredGrid.getRowColData(0,1);
   var ContNo = fm.ContNo.value;
   //alert("document.all(InsuredNo).value4"+document.all("InsuredNo").value);
   <% sql ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+insuredNo+"'"; %>
    var strSQLtemp1 ="select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+InsuredNo+"'";
    arrResult=easyExecSql(strSQLtemp1);
    //alert(arrResult);
    if(arrResult!=null)
    {
        displayInsuredInfo();
        //alert("document.all(InsuredNo).value4.1"+document.all("InsuredNo").value);
    }

    <% 
    	sql ="select * from LCInsured where ContNo = '"+contNo+"' and InsuredNo='"+insuredNo+"'"; 
       TransferData sTransferData11=new TransferData();
	   sTransferData11.setNameAndValue("SQL", sql);
	   VData sVData11 = new VData();
	   sVData11.add(sTransferData11);
	   BusinessDelegate tBusinessDelegate11=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS11 = new SSRS();
	   if(tBusinessDelegate11.submitData(sVData11, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate11.getResult();
	 	  MRSSRS11 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS11;
//    execQuery(sql);
    	
    	//如果没有录入被保人，可能导致错误
    	
    	String insuredAddressNo = ssrs.GetText(1,11);
    %>
    //arrResult=decodeResult("<%=ssrs.encode()%>");
    //alert("document.all(InsuredNo).value5"+document.all("InsuredNo").value);
    var strSQLtemp ="select * from LCInsured where ContNo = '"+ContNo+"' and InsuredNo='"+InsuredNo+"'";
	//alert("document.all(InsuredNo).value6"+document.all("InsuredNo").value);
    arrResult=easyExecSql(strSQLtemp);
    //alert(arrResult);
    if(arrResult!=null)
    {
        DisplayInsured();
    }

    var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
    //alert("arrResult[0][10]=="+arrResult[0][10]);
    fm.InsuredAddressNo.value=tAddressNo;

    //显示被保人地址信息
    
    <% sql ="select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='"+insuredAddressNo+"' and b.CustomerNo='"+insuredNo+"'";
       TransferData sTransferData12=new TransferData();
	   sTransferData12.setNameAndValue("SQL", sql);
	   VData sVData12 = new VData();
	   sVData12.add(sTransferData12);
	   BusinessDelegate tBusinessDelegate12=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS12 = new SSRS();
	   if(tBusinessDelegate12.submitData(sVData12, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate12.getResult();
	 	  MRSSRS12 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS12;
//    execQuery(sql);
    %>
    arrResult=decodeResult("<%=ssrs.encode()%>");
    displayDetailAddress2(arrResult)

    <% sql = "select PolNo,RiskCode,(select RiskName from LMRiskApp where RiskCode=LCPol.RiskCode),Prem,Amnt from LCPol where InsuredNo='"+insuredNo+"' and ContNo='"+contNo+"'"; 
       TransferData sTransferData13=new TransferData();
	   sTransferData13.setNameAndValue("SQL", sql);
	   VData sVData13 = new VData();
	   sVData13.add(sTransferData13);
	   BusinessDelegate tBusinessDelegate13=BusinessDelegate.getBusinessDelegate();
	   String MRStr13 = new String();
	   if(tBusinessDelegate13.submitData(sVData13, "getOneValue", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate13.getResult();
	 	  MRStr13 = (String)responseVData.getObjectByObjectName("String",0);
	   }	
    %>
    displayML("<%=MRStr13%>",PolGrid);


    <% sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+insuredNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
       TransferData sTransferData14=new TransferData();
	   sTransferData14.setNameAndValue("SQL", sql);
	   VData sVData14 = new VData();
	   sVData14.add(sTransferData14);
	   BusinessDelegate tBusinessDelegate14=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS14 = new SSRS();
	   if(tBusinessDelegate14.submitData(sVData14, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate14.getResult();
	 	  MRSSRS14 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS14;
//    execQuery(sql);
    %>
    arrResult=decodeResult("<%=ssrs.encode()%>");
    
	  <% sql="select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='"+insuredNo+"' and ContNo='"+contNo+"' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
	   TransferData sTransferData15=new TransferData();
	   sTransferData15.setNameAndValue("SQL", sql);
	   VData sVData15 = new VData();
	   sVData15.add(sTransferData15);
	   BusinessDelegate tBusinessDelegate15=BusinessDelegate.getBusinessDelegate();
	   SSRS MRSSRS15 = new SSRS();
	   if(tBusinessDelegate15.submitData(sVData15, "execSQL", "ExeSQLUI"))
	   {
	 	  VData responseVData = tBusinessDelegate15.getResult();
	 	  MRSSRS15 = (SSRS)responseVData.getObjectByObjectName("SSRS",0);
	   }
	   ssrs = MRSSRS15;
//	  execQuery(sql);
    %>
    arrResult1=decodeResult("<%=ssrs.encode()%>");

	    try{document.all('Income').value= arrResult[0][0];}catch(ex){};
	    try{document.all('IncomeWay').value= arrResult1[0][0];}catch(ex){};

    <% //sql ="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+insuredNo+"' and ContNo='"+contNo+"' and CustomerNoType='1' ";
			 //execQuery(sql);
		%>
		
		var aSQL="select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='"+InsuredNo+"' and ContNo='"+ContNo+"' and CustomerNoType='1' ";
		   turnPage.queryModal(aSQL, ImpartInsuredGrid);
		//prompt("",aSQL);
 	//alert("document.all(InsuredNo).value7"+document.all("InsuredNo").value);
    //getImpartDetailInfo();
    getAge2();
    //在录单过程中不需要进行客户合并，故注释掉　hl 20050505
    //如果是复核状态，则进行重复客户校验
    if(LoadFlag=="5"){
      InsuredChkNew();
      return;
    }
    //InsuredNo
	//alert("document.all(InsuredNo).value8"+document.all("InsuredNo").value);
<%}}%>
	
}


function decodeResult(str)
{        
  if (!str) {
    return null;
  }      
  else if (str.substring(0, str.indexOf("|")) != "0") {
    return null;
  }      
  else { 
    return decodeEasyQueryResult(str);
  }      
}

function displayML(strResult,grid)
{
        turnPage.strQueryResult  = strResult;
        //alert("turnPage==="+turnPage.strQueryResult);
        //判断是否查询成功
        if (!strResult || strResult.substring(0, strResult.indexOf("|")) != "0")
        {
        	grid.clearData();
        	return;
      	}
        
        turnPage.useSimulation = 1;
        
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = grid;
        //保存SQL语句
        turnPage.strQuerySql = "";
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
}

//投连险种的投资计划
function initInvestPlanRate()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1] = new Array();
      iArray[1][0] = "投资帐户号";    	        //列名
      iArray[1][1] = "80px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "投资帐户名称";         		//列名
      iArray[2][1] = "100px";            		//列宽
      iArray[2][2] = 60;            			//列最大值
      iArray[2][3] = 0;                   			//是否允许输入,1表示允许，0表示不允许
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "投资比例上限";         		//列名
      iArray[3][1] = "0px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "投资比例下限";         		//列名
      iArray[4][1] = "0px";            		//列宽
      iArray[4][2] = 50;            			//列最大值
      iArray[4][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5] = new Array();
      iArray[5][0] = "投资比例";         		//列名
      iArray[5][1] = "80px";            		//列宽
      iArray[5][2] = 50;            			//列最大值
      iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[6] = new Array();
      iArray[6][0] = "缴费编号";         		//列名
      iArray[6][1] = "0px";            		//列宽
      iArray[6][2] = 100;            			//列最大值
      iArray[6][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

        
      /*iArray[7] = new Array();
      iArray[7][0] = "";         		//列名
      iArray[7][1] = "80px";            		//列宽
      iArray[7][2] = 100;            			//列最大值
      iArray[7][3] = 1;


     
      iArray[8] = new Array();
      iArray[8][0] = "";         		//列名
      iArray[8][1] = "80px";            		//列宽
      iArray[8][2] = 100;            			//列最大值
      iArray[8][3] = 1;*/
 
      InvestPlanRate = new MulLineEnter( "fm" , "InvestPlanRate" );
      //这些属性必须在loadMulLine前
      InvestPlanRate.mulLineCount = 0;
     InvestPlanRate.displayTitle = 1;
     InvestPlanRate.hiddenPlus = 1;
      InvestPlanRate.hiddenSubtraction = 1;
      InvestPlanRate.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

</script> 
