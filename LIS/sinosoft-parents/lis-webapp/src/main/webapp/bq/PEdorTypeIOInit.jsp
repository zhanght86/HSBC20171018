
<%
	//PEdorTypeICInit.jsp
	//程序功能：职业类别变更
	//创建日期：2005-5-17 11:48上午
	//创建人  ：Lihs
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

function initForm()
{
  try
  {
    Edorquery();
    initInpBox();
    initPolOldGrid();
    initImpartGrid();
    if (fm.AppObj.value != "G")
    {
        QueryCustomerInfo();
        divPerson.style.display = "";
    }
    queryInsuredOccupationInfo();
    queryPolInfo();
    initPolNewGrid();
    querychgOcc();
    querytImpartGrid();
  }
  catch(re)
  {
    alert("PEdorTypeIOInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

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
      iArray[1][4]="impartver";
      //add by jiaqiangli 2009-03-12 保全只取健康告知与财务告知
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A01#,#A02#)";
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
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      //iArray[2][16]="#02#";
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

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 0;
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {

    }
}

function initInpBox()
{
    try
    {
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        //document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        //document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        try{document.all('AppObj').value = top.opener.document.all('AppObj').value; }catch(ex){}
        showOneCodeName('EdorType', 'EdorType', 'EdorTypeName');
    }
    catch(ex)
    {
        alert("在PEdorTypeIOInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

//险种信息列表
function initPolOldGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="险种代码";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="基本保额/份数";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="保费标准";
        iArray[4][1]="40px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="职业加费";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[4][23]="0";
        
        iArray[6]=new Array();
				iArray[6][0]="币种";
				iArray[6][1]="60px";
				iArray[6][2]=100;
				iArray[6][3]=2;
				iArray[6][4]="currency";

        PolOldGrid = new MulLineEnter( "fm" , "PolOldGrid" );
        //这些属性必须在loadMulLine前
        PolOldGrid.mulLineCount = 0;
        PolOldGrid.displayTitle = 1;
        PolOldGrid.canSel=0;
        //PolOldGrid.canChk=1;
        PolOldGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolOldGrid.hiddenSubtraction=1;
        PolOldGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
    }
    catch(ex)
    {
        alert(ex);
    }
}

function initQuery()
{
    var i = 0;
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "QUERY||DETAIL";
    mFlag = "N";
    fm.submit();
}

function CondQueryClick()
{
    var i = 0;
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    document.all('fmtransact').value = "QUERY||MAIN";
    var tContNo = document.all('ContNo').value;
    var tCustomerNo=document.all('CustomerNo1').value;

    if (tContNo==null&&tContNo==''&&tCustomerNo!=null&&tCustomerNo!='')
        mFlag = "P";
    else if(tCustomerNo==null&&tCustomerNo==''&&tContNo!=null&&tContNo!='')
        mFlag = "C";
    else if (tCustomerNo==null&&tCustomerNo==''&&tContNo==null&&tContNo=='')
        mFlag = "N";
    else
        mFlag = "A";
    fm.submit();
}

function detailQueryClick()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('fmtransact').value = "QUERY||DETAIL";

    var tSel=LCInsuredGrid.getSelNo();

    if (tSel==0||tSel==null)
        alert("不能是空记录!");
    else
    {
        var tCustomerNo =LCInsuredGrid.getRowColData(tSel-1,2);
        document.all('CustomerNo1').value =tCustomerNo;
        document.all('ContNo').value =LCInsuredGrid.getRowColData(tSel-1,1);
        fm.submit();
    }
}

function newOccupatioinInfo(){
//      var strsql = "select OccupationCode,OccupationType from lpinsured where 1=1 and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"' and insuredno = '"+top.opener.document.all('InsuredNo').value+"'";
     var strsql = "";
     var sqlid1="PEdorTypeIOInputSql14";
     var mySql1=new SqlClass();
     mySql1.setResourceName("bq.PEdorTypeIOInputSql"); //指定使用的properties文件名
     mySql1.setSqlId(sqlid1);//指定使用的Sql的id
     mySql1.addSubPara(top.opener.document.all('EdorNo').value);//指定传入的参数
     mySql1.addSubPara(top.opener.document.all('EdorType').value);//指定传入的参数
     mySql1.addSubPara(top.opener.document.all('InsuredNo').value);//指定传入的参数
     strsql=mySql1.getString();
     var aResult = easyExecSql(strsql,1,0);
   if(aResult != null){
      document.all('OccupationCode').value = aResult[0][0];
      document.all('OccupationType').value = aResult[0][1];
   }
}

function initDiv()
{
    divLPInsuredDetail.style.display ='none';
    divDetail.style.display='none';
}

//险种信息列表
function initPolNewGrid()
{
    var iArray = new Array();
    try
    {
       iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="险种代码";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="险种名称";
        iArray[2][1]="120px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="基本保额/份数";
        iArray[3][1]="60px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="保费标准";
        iArray[4][1]="40px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="职业变更补/退费";
        iArray[5][1]="40px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";
        
        iArray[6]=new Array();
				iArray[6][0]="币种";
				iArray[6][1]="60px";
				iArray[6][2]=100;
				iArray[6][3]=2;
				iArray[6][4]="currency";

        PolNewGrid = new MulLineEnter("fm", "PolNewGrid");
        //这些属性必须在loadMulLine前
        PolNewGrid.mulLineCount = 0;
        PolNewGrid.displayTitle = 1;
        PolNewGrid.canSel=0;
        //PolOldGrid.canChk=1;
        PolNewGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        PolNewGrid.hiddenSubtraction=1;
        PolNewGrid.loadMulLine(iArray);
        //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
          alert(ex);
      }
}

</script>
