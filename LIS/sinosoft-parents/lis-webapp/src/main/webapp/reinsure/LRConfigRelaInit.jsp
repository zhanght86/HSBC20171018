<%@include file="/i18n/language.jsp"%>
<html>
<%
//name :LRAccRDInit.jsp
//function :Manage 
//Creator :
//date :2007-3-5
%>

<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.vdb.*"%>
<%@page import = "com.sinosoft.lis.vbl.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 		GlobalInput tG = new GlobalInput(); 
  	tG=(GlobalInput)session.getAttribute("GI");
  	String Operator=tG.Operator;
  	String Comcode=tG.ManageCom;
 		String CurrentDate= PubFun.getCurrentDate();   
    String tCurrentYear=StrTool.getVisaYear(CurrentDate);
    String tCurrentMonth=StrTool.getVisaMonth(CurrentDate);
    String tCurrentDate=StrTool.getVisaDay(CurrentDate);     
    String tAccumulateDefNO=request.getParameter("accumulatedefno");
    System.out.println(tAccumulateDefNO);
 %>
<script type="text/javascript">
function initInpBox(){
  try{
  	fm.AccumulateDefNO.value="<%=tAccumulateDefNO%>";
  	fm.UNIONACCNO.value="";
  	//fm.AccumulateMode.value="02";
  	//fm.AccumulateModeName.value="";
  	//fm.RiskAmntFlag.value="";
  	//fm.RiskAmntFlagName.value="";
  	//fm.GetDutyFlag.value="02";
  	//fm.GetDutyFlagName.value="";
  
  
    
      
  }
  catch(ex){
    myAlert("��ʼ���������!");
  }
}
;

// ������ĳ�ʼ��
function initSelBox(){
  try{
  }
  catch(ex){
    myAlert("2��CertifyDescInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm(){
  try{
    initInpBox();
    
    initSelBox();
    initRelateGrid();
    initRGrid();
       
    qeuryGrid();
  }
  catch(re){
    myAlert(re+"3CertifyDescInit.jsp-->"+"��ʼ���������!");
  }
}

function initRelateGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

	iArray[1]=new Array();
    iArray[1][0]="�ۼƷ��ձ���";    //����
    iArray[1][1]="50px";        //�п�
    iArray[1][2]=200;           //�����ֵ
    iArray[1][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[2]=new Array();
    iArray[2][0]="�����ۼƱ���";    //����
    iArray[2][1]="50px";        //�п�
    iArray[2][2]=200;           //�����ֵ
    iArray[2][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������
    
    
    
    
    
    RelateGrid = new MulLineEnter( "fm" , "RelateGrid" );
    RelateGrid.mulLineCount = 1;
    RelateGrid.displayTitle = 1;
    RelateGrid.locked=1;
    RelateGrid.canSel=1;
    RelateGrid.selBoxEventFuncName ="showRela"; //��Ӧ�ĺ���������������   
    RelateGrid.loadMulLine(iArray);
    RelateGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

function initRGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

	iArray[1]=new Array();
    iArray[1][0]="���ֱ���"; 
    iArray[1][1]="30px"; 
    iArray[1][2]=100; 
    iArray[1][3]=2; 
    iArray[1][4]="lrrecode"; 
		iArray[1][5]="1|2"; 	 			//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="0|1";	
		iArray[1][15]="accumulatedefno";
		iArray[1][16]=fm.all("AccumulateDefNO").value;
		iArray[1][19]=1; 

    iArray[2]=new Array();
    iArray[2][0]="��������";    //����
    iArray[2][1]="50px";        //�п�
    iArray[2][2]=200;           //�����ֵ
    iArray[2][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������
    
    
    
    
    
    RGrid = new MulLineEnter( "fm" , "RGrid" );
    RGrid.mulLineCount = 1;
    RGrid.displayTitle = 1;
    //RelateGrid.canSel=1;
    RGrid.loadMulLine(iArray);
    RGrid.detailInfo="������ʾ��ϸ��Ϣ";
    
  
    
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}



</script>


