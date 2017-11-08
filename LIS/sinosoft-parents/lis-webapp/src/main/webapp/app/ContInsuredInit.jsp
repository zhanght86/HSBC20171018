 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="ContInsuredInput.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>
<script language="JavaScript">
function initInpBox()
{
    try
    {
        fm.SelPolNo.value='';
        try{mSwitch.deleteVar("PolNo");}catch(ex){};
    }
    catch(ex)
    {
        alert("在ContInsuredInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initSelBox()
{
    try
    {
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
    }
    catch(ex)
    {
        alert("在ContInsuredInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
    
        if(mSwitch.getVar('ProposalGrpContNo')==false||mSwitch.getVar('ProposalGrpContNo')==null||mSwitch.getVar('ProposalGrpContNo')==""||mSwitch.getVar('ProposalGrpContNo')=="null")
         {
            mSwitch.deleteVar('ProposalGrpContNo');
            mSwitch.addVar('ProposalGrpContNo','',GrpContNo);
         }
        initInpBox();
        initSelBox();
        initInsuredGrid();
        initImpartGrid();
        //initImpartDetailGrid();
        
        initPolGrid();
        initGrpInfo();
        //alert("a"); 
        if (scantype== "scan")
        {
          setFocus();
        }
        //    alert("b");
        if(Auditing=="1")
        {
        	var tmSwitch = top.opener.parent.VD.gVSwitch;
        	mSwitch.deleteVar( "ContNo" );
        	mSwitch.addVar( "ContNo", "", tmSwitch.getVar("ContNo") );
        	mSwitch.updateVar("ContNo", "", tmSwitch.getVar("ContNo"));
        	mSwitch.deleteVar( "ProposalContNo" );
        	mSwitch.addVar( "ProposalContNo", "", tmSwitch.getVar("ContNo") );
        	mSwitch.updateVar("ProposalContNo", "", tmSwitch.getVar("ContNo"));
        	mSwitch.deleteVar( "PrtNo" );
        	mSwitch.addVar( "PrtNo", "", tmSwitch.getVar("PrtNo") );
        	mSwitch.updateVar("PrtNo", "", tmSwitch.getVar("PrtNo"));
        	mSwitch.deleteVar( "GrpContNo" );
        	mSwitch.addVar( "GrpContNo", "", tmSwitch.getVar("GrpContNo") );
        	mSwitch.updateVar("GrpContNo", "", tmSwitch.getVar("GrpContNo"));
        }

        getContInfo();
        getInsuredInfo();

        DivGrpNoname.style.display="";
        if(document.all('FamilyType').value==''||document.all('FamilyType').value==null||document.all('FamilyType').value=='0'||document.all('FamilyType').value=='false')
        {
            document.all('FamilyType').value='0';
            divTempFeeInput.style.display="none";
            getProposalInsuredInfo();          //获得个人单信息，填写被保人表
            getoccupationtype();
            getAge(); 
            
            //显示代码汉字
            if (ContType=="2")
            {
                showCodeName();
            }
            
            if (NameType == "null" || NameType == "" || NameType == null)
                NameType = "0";
            document.all('PolTypeFlag').value=NameType;
            
            if(NameType=="0")
            {
                //alert(111); 
                document.all('PolTypeFlagName').value="个人单";
                document.all('InsuredPeoples').readOnly=true;
                document.all('InsuredPeoples').value="1";
                document.all('InsuredAppAge').readOnly=true; 
                fm.ListState.verify = "";
                divListState.style.display = "none";
            }
            else if(NameType=="1")
            {
                DivLCBasicInfo.style.display="none"; 
                divListState.style.display = "";
                DivRelation.style.display="none";    
                document.all('PolTypeFlagName').value="无名单"; 
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false; 
                fm.ListState.verify = "是否激活|notnull&code:ListState";
                fm.Sex.verify = "";
              	fm.IDNo.verify = "";
              	fm.OccupationCode.verify = "";
                document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"被保险人出生日期|Date\" verifyorder='2' >";
                document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  verify='被保险人职业类别|code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
           }
           else if(NameType=="2")
            {
                DivLCBasicInfo.style.display="none";
                divListState.style.display = "none"; 
                DivRelation.style.display="none";    
                document.all('PolTypeFlagName').value="公共帐户"; 
                document.all('InsuredPeoples').readOnly=false;
                document.all('InsuredAppAge').readOnly=false; 
                fm.ListState.verify = "";
                fm.Sex.verify = "";
              	fm.IDNo.verify = "";
              	fm.OccupationCode.verify = "";
                document.all("OccupationTypeID").innerHTML = "<Input class=code name=OccupationType  verify='被保险人职业类别|code:OccupationType' ondblclick=\"OccupationTypeDBLClick();\" onkeyup=\"OccupationTypeKeyUP();\">";
                document.all("BirthdayID").innerHTML = "<input class=common dateFormat=short onblur=\" checkinsuredbirthday(); getAge();\" name=Birthday verify=\"被保险人出生日期|Date\" verifyorder='2' >";
           }
        }
    }
    catch(re)
    {
        alert("ContInsuredInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
    if (ContType=="2")
    {
        initContPlanCode();
        initExecuteCom();
    }
    if(LoadFlag=="1")
    {
       //divLCInsuredPerson.style.display="none";
       divFamilyType.style.display="";
       divPrtNo.style.display="";
       divSamePerson.style.display="";
       DivGrpNoname.style.display="none";
       fm.PolTypeFlag.value='0';
    }
    if(LoadFlag=="2")
    {
        fm.InsuredSequencename.value="被保险人资料";
        divFamilyType.style.display="";
        divSamePerson.style.display="none";
        divPrtNo.style.display="none";
        divTempFeeInput.style.display="none";
        divInputContButton.style.display="none";
        divGrpInputContButton.style.display="";
        //divLCInsuredPerson.style.display="none";
        DivRelation.style.display="none";
        DivAddress.style.display="";
        DivClaim.style.display="none";
    }
    if(LoadFlag=="3"){
        divTempFeeInput.style.display="";
        getInsuredInfo();
    }
    if(LoadFlag=="4"){
        fm.InsuredSequencename.value="被保险人资料";
        divFamilyType.style.display="none";
        divPrtNo.style.display="none";
        divInputContButton.style.display="none";
        divApproveContButton.style.display="";
        divTempFeeInput.style.display="none";
        divAddDelButton.style.display="none";
        divSamePerson.style.display="none";
        //divLCInsuredPerson.style.display="none";
        divCheckInsuredButton.style.display="none";
        DivRelation.style.display="none";
        DivAddress.style.display="none";
        DivClaim.style.display="none";
        getInsuredInfo();
    }
    if(LoadFlag=="5"||LoadFlag=="25"){
        divTempFeeInput.style.display="";
        getInsuredInfo();
        divFamilyType.style.display="";
        divAddDelButton.style.display="";
        fm.butBack.style.display="none";
        divSamePerson.style.display="none";
        divInputContButton.style.display="none";
        divApproveContButton.style.display="";
        //divLCInsuredPerson.style.display="none";
    }
    
    
    if(LoadFlag=="6"){
        divTempFeeInput.style.display="";
        divPrtNo.style.display="";
        divInputContButton.style.display="none";
        divQueryButton.style.display="";
        divFamilyType.style.display="";
        divAddDelButton.style.display="none";
        fm.butBack.style.display="none";
        divSamePerson.style.display="none";
    }
    if(LoadFlag=="7"){
    	divContType.style.display = "none";
        DivGrpNoname.style.display="";
        DivGrpNoname1.style.display="";
        DivGrpNoname2.style.display="";
        DivGrpNoname3.style.display="none";
        DivGrpNoname4.style.display="none"
        DivGrpNoname5.style.display="none";
        DivGrpNoname6.style.display="none"
        DivRelation.style.display="none";
        divFamilyType.style.display="none";
        divSamePerson.style.display="none";
        divPrtNo.style.display="none";
        divTempFeeInput.style.display="none";
        divInputContButton.style.display="none";
        divGrpInputContButton.style.display="";        
        //divLCInsuredPerson.style.display="none";
        fm.InsuredSequencename.value="被保险人资料";
    }
    if(LoadFlag=="16"){
        fm.InsuredSequencename.value="被保险人资料";
        divTempFeeInput.style.display="none";
        divInputContButton.style.display="none";
        divQueryButton.style.display="";
        divFamilyType.style.display="none";
        divAddDelButton.style.display="none";
        fm.butBack.style.display="none";
        divSamePerson.style.display="none";
        //divLCInsuredPerson.style.display="none";
        DivRelation.style.display="none";
        //DivAddress.style.display="none";
        DivClaim.style.display="none";
    }
    if(LoadFlag=="13"){
        fm.InsuredSequencename.value="被保险人资料";
        divFamilyType.style.display="none";
        divSamePerson.style.display="none";
        divPrtNo.style.display="none";
        DivRelation.style.display="none";
        divTempFeeInput.style.display="none";
        divInputContButton.style.display="none";
        //divLCInsuredPerson.style.display="none";
        divApproveModifyContButton.style.display="";
        getInsuredInfo();
    }
    if(LoadFlag=="23")
      {
        DivRelation.style.display="none";
      }
    if(this.LoadFlag == "99"&&checktype=="1"){
        divAddDelButton.style.display="none";
        divInputContButton.style.display="none";
        autoMoveButton.style.display="";
    }
    if(this.LoadFlag == "99"&&checktype=="2"){
        divAddDelButton.style.display="none";
        divInputContButton.style.display="none";
        autoMoveButton.style.display="";
    }
    if(this.display == "0")
    {
        divFamilyType.style.display="none";
    } 
}


// 暂收费信息列表的初始化
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

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" );
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 0;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid.hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
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
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="GrpImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
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
// 告知明细信息列表的初始化
function initImpartDetailGrid() {
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
      iArray[1][4]="ImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
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


      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" );
      //这些属性必须在loadMulLine前
      ImpartDetailGrid.mulLineCount = 1;
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);

    }
    catch(ex) {
      alert(ex);
    }
}
//被保人险种信息列表初始化
function initPolGrid(){
    var iArray = new Array();


    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		        //列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保险单险种号码";         		//列名
      iArray[1][1]="60px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]=" 险种编码";         		//列名
      iArray[2][1]="60px";            		        //列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]=" 保费(元)";         		//列名
      iArray[3][1]="60px";            		        //列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额(元)";         		//列名
      iArray[4][1]="60px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


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
    
    
    //判断若PrtNo从内存中没取到值时，取投保单号
    if(document.all('PrtNo').value=="false"||document.all( 'PrtNo' ).value==null||document.all( 'PrtNo' ).value=="null")
    {
      try { document.all( 'PrtNo' ).value = mSwitch.getVar( "ProposalGrpContNo" ); } catch(ex) { };
    }
    try { document.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalContNo" ); } catch(ex) { };
    try { document.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { document.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    try { document.all( 'PolTypeFlag' ).value = NameType} catch(ex) {};
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
	fm.PrtNo.value=mSwitch.getVar('PrtNo');
	fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	fm.ManageCom.value=mSwitch.getVar('ManageCom');
	fm.AgentCode.value=mSwitch.getVar('AgentCode');
	fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
	fm.GrpName.value=mSwitch.getVar('GrpName');
	fm.CValiDate.value=mSwitch.getVar('CValiDate');
	fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}
</script>
