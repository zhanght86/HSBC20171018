<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-08-21
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
 %>
<script type="text/javascript">
function initInpBox()
{
  try
  {
  	fm.FeeTableNo.value 	=	"";
  	fm.FeeTableName.value =	"";
  	fm.FeeTableType.value =	"";
  	fm.ReMark.value =	"";
  	fm.State.value ="02";
  	fm.stateName.value ="δ��Ч";
  	
  }         
  catch(ex) 
  {
    myAlert("���г�ʼ���ǳ��ִ���");
  }
}
;

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("��LRContInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initTableClumnDefGrid();
    verifyFeeRateTableImp();
  }
  catch(re)
  {
    myAlert("ReContManageInit.jsp-->"+"��ʼ���������!");
  }
}


//�ٱ���ͬǩ�����б��ʼ��
function initTableClumnDefGrid() 
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";
    iArray[0][1]="30px";
    iArray[0][2]=10;
    iArray[0][3]=0;
    
    iArray[1]=new Array();    
    iArray[1][0]="���ʱ��ֶ�";
    iArray[1][1]="60px";      
    iArray[1][2]=100;         
    iArray[1][3]=1;  
    iArray[1][9]="���ʱ��ֶ���|notnull";
    iArray[1][19]=1;
    
    iArray[2]=new Array();    
    iArray[2][0]="�ӿڱ��ֶ���";
    iArray[2][1]="60px";      
    iArray[2][2]=100;         
    iArray[2][3]=2;  
    iArray[2][10]="AccType"; 
    iArray[2][11]="0|^GrpContNo|�����ͬ����(�ַ���)^ContNo|��ͬ����(�ַ���)^RiskCode|���ֱ���(�ַ���)^DutyCode|���α���(�ַ���)^Years|��������(������)^InsuredYear|�������(������)^CValiDate|������Ч����(������)^EndDate|����������ֹ����(������)^InsuredNo|�����˿ͻ�����(�ַ���)^InsuredSex|�������Ա�(�ַ���)^InsuredAge|����������(�ַ���)^OccupationType|������ְҵ���(�ַ���)^StandPrem|��׼����(������)^Prem|ʵ�ʱ���(������)^RiskAmnt|���ձ���(������)^Amnt|��������(������)^Mult|����(������)^PayIntv|���Ѽ��(������)^PayYears|��������(������)^AcciEndDate|����������ֹ����(������)^GetStartDate|��������(������)^GetStartType|�������ڼ�������(�ַ�)^GetRate |�⸶����(������)^GetIntv |��ȡ���(������)^PayCount|��������(������)^PayMoney|���ѽ��(������)^LastPayToDate|ԭ��������(������)^CurPayToDate |�ֽ�������(������)^EdorNo|������(�ַ���)^FeeOperationType|����ҵ������(�ַ���)^FeeFinaType|���Ĳ�������(�ַ���)^ClmNo|�ⰸ��(�ַ���)^ClmFeeOperationType|�ⰸҵ������(�ַ���)^ClmFeeFinaType|�ⰸ��������(�ַ���)^StandGetMoney|��׼���(������)^ClmGetMoney|ʵ�����(������)^GetCValiDate|��Ч����(������)^GetDate|ҵ������(������)^ContPlanCode|���ռƻ�����^StandbyString2|����ȼ�^StandbyString3|�ƻ���";
		iArray[2][12]="2";
		iArray[2][13]="0";  
    iArray[2][9]="�ӿڱ��ֶ�|notnull";         
    iArray[2][19]=1;
    
    
    
    iArray[3]=new Array();
    iArray[3][0]="���ʱ��ֶ�������";
    iArray[3][1]="80px";
    iArray[3][2]=100;
    iArray[3][3]=2;
    iArray[3][10]="AccType0"; 
    iArray[3][11]="0|^A01|�ַ��͹̶�ֵ^A02|�ַ�������ֵ^N01|��ֵ�͹̶�ֵ^N02|��ֵ������ֵ";
		iArray[3][12]="3|4";
		iArray[3][13]="1|0";  
    iArray[3][19]=1;
    
    iArray[4]=new Array();
    iArray[4][0]="���ʱ��ֶ�����";
    iArray[4][1]="80px";
    iArray[4][2]=100;
    iArray[4][3]=0;
    iArray[4][9]="�ӿڱ��ֶ�|notnull";
    iArray[4][19]=1;
    
    iArray[5]=new Array();
    iArray[5][0]="�̶�ֵӳ���ֶ���";  //����
    iArray[5][1]="80px";           	//�п�
    iArray[5][2]=200;            		//�����ֵ
    iArray[5][3]=2;              	  //�Ƿ���������,1��ʾ����0��ʾ������   
    iArray[5][10]="AccType1"; 
    iArray[5][11]="0|^|^NumOne|�����ֶ�һ^NumTwo|�����ֶζ�^NumThree|�����ֶ���^NumFour|�����ֶ���^NumFive|�����ֶ���^NumSix|�����ֶ���^NumSeven|�����ֶ���^NumEight|�����ֶΰ�^NumNine|�����ֶξ�^NumTen|�����ֶ�ʮ^StrOne|�ַ��ֶ�һ^StrTwo|�ַ��ֶζ�^StrThree|�ַ��ֶ���^StrFour|�ַ��ֶ���^StrFive|�ַ��ֶ���|";
		iArray[5][12]="5|6";
		iArray[5][13]="0|1";
		iArray[5][19]=1;
		
		iArray[6]=new Array();
    iArray[6][0]="�̶�ֵӳ���ֶ�";
    iArray[6][1]="0px";
    iArray[6][2]=200;
    iArray[6][3]=3;
    iArray[6][19]=1;
	
    iArray[7]=new Array();
    iArray[7][0]="����������ӳ���ֶ���";         		//����
    iArray[7][1]="80px";            //�п�
    iArray[7][2]=60;            		//�����ֵ
    iArray[7][3]=2;              		//2��ʾ�Ǵ���ѡ��
    iArray[7][10]="AccType2"; 
    iArray[7][11]="0|^|^NumOne|�����ֶ�һ^NumTwo|�����ֶζ�^NumThree|�����ֶ���^NumFour|�����ֶ���^NumFive|�����ֶ���^NumSix|�����ֶ���^NumSeven|�����ֶ���^NumEight|�����ֶΰ�^NumNine|�����ֶξ�^NumTen|�����ֶ�ʮ^StrOne|�ַ��ֶ�һ^StrTwo|�ַ��ֶζ�^StrThree|�ַ��ֶ���^StrFour|�ַ��ֶ���^StrFive|�ַ��ֶ���|"	 ;
		iArray[7][12]="7|8";
		iArray[7][13]="0|1";
		iArray[7][19]=1;
    
    iArray[8]=new Array();
    iArray[8][0]="�����������ֶ�ӳ��";
    iArray[8][1]="0px";
    iArray[8][2]=200;
    iArray[8][3]=3;
    iArray[8][19]=1;

    iArray[9]=new Array();
    iArray[9][0]="���������ֶ�ӳ����";        //����
    iArray[9][1]="90px";           //�п�
    iArray[9][2]=60;            		//�����ֵ
    iArray[9][3]=2;              		//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[9][10]="AccType3"; 
    iArray[9][11]="0|^|^NumOne|�����ֶ�һ^NumTwo|�����ֶζ�^NumThree|�����ֶ���^NumFour|�����ֶ���^NumFive|�����ֶ���^NumSix|�����ֶ���^NumSeven|�����ֶ���^NumEight|�����ֶΰ�^NumNine|�����ֶξ�^NumTen|�����ֶ�ʮ^StrOne|�ַ��ֶ�һ^StrTwo|�ַ��ֶζ�^StrThree|�ַ��ֶ���^StrFour|�ַ��ֶ���^StrFive|�ַ��ֶ���|"	 ;
		iArray[9][12]="9|10";
		iArray[9][13]="0|1";
		iArray[9][19]=1;
    
		iArray[10]=new Array();
    iArray[10][0]="���������ֶ�ӳ��";
    iArray[10][1]="0px";
    iArray[10][2]=200;
    iArray[10][3]=3;
    iArray[10][19]=1;
    
    TableClumnDefGrid = new MulLineEnter( "fm" , "TableClumnDefGrid" );
    TableClumnDefGrid.mulLineCount = 1;   
	  TableClumnDefGrid.displayTitle = 1;
//  TableClumnDefGrid.locked = 0;
	  TableClumnDefGrid.canSel =0; 
//  TableClumnDefGrid.hiddenPlus=0; 
//  TableClumnDefGrid.hiddenSubtraction=1;
	  TableClumnDefGrid.loadMulLine(iArray); 
  }
  
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}

</script> 