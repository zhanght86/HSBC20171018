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
 %>
<script type="text/javascript">
function initInpBox(){
  try{
  	fm.AccumulateDefNO.value="";
  	fm.AccumulateDefName.value="";
  	//fm.AccumulateMode.value="02";
  	fm.AccumulateModeName.value="";
  	fm.RiskAmntFlag.value="";
  	fm.RiskAmntFlagName.value="";
  	//fm.GetDutyFlag.value="02";
  	fm.GetDutyFlagName.value="";
  	fm.State.value="02";
    fm.stateName.value="δ��Ч";
    
    fm.DeTailFlag.value="01";
    fm.DeTailFlagName.value="�������ּ���";
    
    if(fm.DeTailFlag.value="01")
    {
      fm.DeTailType.value="RISK";
    }else{
    	fm.DeTailType.value="DUTY";
    	}  
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
    initDutyGrid();
  }
  catch(re){
    myAlert("3CertifyDescInit.jsp-->"+"��ʼ���������!");
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
    iArray[1][0]="���ֱ���"; 
    iArray[1][1]="30px"; 
    iArray[1][2]=100; 
    iArray[1][3]=2; 
    iArray[1][4]="llclaimrisk"; 
		iArray[1][5]="1|2"; 	 			//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 

    iArray[2]=new Array();
    iArray[2][0]="��������";    //����
    iArray[2][1]="50px";        //�п�
    iArray[2][2]=200;           //�����ֵ
    iArray[2][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="�ۼƱ�־";  //����
    iArray[3][1]="0px";        //�п�
    iArray[3][2]=50;            //�����ֵ
    iArray[3][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][10]="noacc"; 
    iArray[3][11]="0|^|^01|�ۼ�^02|���ۼ�";
		iArray[3][12]="3|4";
		iArray[3][13]="1|0";
    iArray[3][19]=3;
    
    iArray[4]=new Array();
    iArray[4][0]="�ۼƱ�־";  //����
    iArray[4][1]="0px";         //�п�
    iArray[4][2]=50;            //�����ֵ
    iArray[4][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������
    
    RelateGrid = new MulLineEnter( "fm" , "RelateGrid" );
    RelateGrid.mulLineCount = 1;
    RelateGrid.displayTitle = 1;
    //RelateGrid.canSel=1;
    RelateGrid.loadMulLine(iArray);
    RelateGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

function initDutyGrid() {
  var iArray = new Array();
  try{
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;

    iArray[1]=new Array();
    iArray[1][0]="���δ���";
    iArray[1][1]="30px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][4]="lrduty"; 
		iArray[1][5]="1|2"; 	 						//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="0|1";									  
		iArray[1][19]=1;   	

    iArray[2]=new Array();
    iArray[2][0]="��������";     //����
    iArray[2][1]="50px";         //�п�
    iArray[2][2]=200;            //�����ֵ
    iArray[2][3]=1;              //�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="�ۼƱ�־";  //����
    iArray[3][1]="0px";        //�п�
    iArray[3][2]=50;            //�����ֵ
    iArray[3][3]=2;             //�Ƿ���������,1��ʾ����0��ʾ������
    iArray[3][10]="noacc"; 
    iArray[3][11]="0|^|^01|�ۼ�^02|���ۼ�"; 
		iArray[3][12]="3|4";
		iArray[3][13]="1|0";
    iArray[3][19]=3;
    
    iArray[4]=new Array();
    iArray[4][0]="�ۼƱ�־";  //����
    iArray[4][1]="0px";         //�п�
    iArray[4][2]=50;            //�����ֵ
    iArray[4][3]=3;             //�Ƿ���������,1��ʾ����0��ʾ������
    
    DutyGrid = new MulLineEnter( "fm" , "DutyGrid" );
    DutyGrid.mulLineCount = 1;
    DutyGrid.displayTitle = 1;
    //DutyGrid.canSel=1;
    DutyGrid.loadMulLine(iArray);
    DutyGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex){
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

</script>


