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
   fm.RIContNo.value =""; 
   fm.RIContName.value="";
   fm.ContType.value ="";
   fm.ContTypeName.value ="";
   fm.ReInsuranceType.value ="";
   fm.ReInsuranceTypeName.value ="";
       
    fm.RValidate.value ="";
    fm.RInvalidate.value ="";
    fm.RSignDate.value="";
    fm.ModRIContNo.value="";
    fm.ContState.value="0";
    fm.ContstateName.value="δ��Ч";
   
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
    initSignerGrid();
    initFactorGrid();
  
    
  }
  catch(re)
  {
    myAlert("ReContManageInit.jsp-->"+"��ʼ���������!");
  }
}


//�ٱ���ͬǩ�����б��ʼ��
function initSignerGrid() 
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
    iArray[1][0]="��˾����";
    iArray[1][1]="130px";
    iArray[1][2]=100;
    iArray[1][3]=2;
    iArray[1][4]="lrcompanycode"; 
		iArray[1][5]="1|2"; 	 						//�����ô���ֱ���ڵ�1��2
		iArray[1][6]="1|0";
		iArray[1][19]=1;  
    
    
    iArray[2]=new Array();    
    iArray[2][0]="��˾����";
    iArray[2][1]="100px";      
    iArray[2][2]=100;         
    iArray[2][3]=1;
    
    iArray[3]=new Array();
    iArray[3][0]="��ͬǩ����";        //����
    iArray[3][1]="80px";           //�п�
    iArray[3][2]=200;            		//�����ֵ
    iArray[3][3]=1;              	  //�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ְ��";         		//����
    iArray[4][1]="80px";            //�п�
    iArray[4][2]=60;            		//�����ֵ
    iArray[4][3]=1;              		//2��ʾ�Ǵ���ѡ��

    iArray[5]=new Array();
    iArray[5][0]="��ϵ�绰";        //����
    iArray[5][1]="90px";           //�п�
    iArray[5][2]=60;            		//�����ֵ
    iArray[5][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[6]=new Array();
    iArray[6][0]="�ֻ�";         		//����
    iArray[6][1]="90px";           //�п�
    iArray[6][2]=60;            		//�����ֵ
    iArray[6][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="����";         		//����
    iArray[7][1]="90px";           //�п�
    iArray[7][2]=60;            		//�����ֵ
    iArray[7][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[8]=new Array();
    iArray[8][0]="��������";        //����
    iArray[8][1]="180px";           //�п�
    iArray[8][2]=100;            		//�����ֵ
    iArray[8][3]=1;              		//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="��ϵ�˱���";
    iArray[9][1]="70px";
    iArray[9][2]=100;
    iArray[9][3]=3;
		
    
    SignerGrid = new MulLineEnter( "fm" , "SignerGrid" );
    SignerGrid.mulLineCount = 0;   
	  SignerGrid.displayTitle = 1;
//  SignerGrid.locked = 0;
	  SignerGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
//  SignerGrid.hiddenPlus=0; 
//  SignerGrid.hiddenSubtraction=1;
	  SignerGrid.loadMulLine(iArray); 
    SignerGrid.detailInfo="������ʾ��ϸ��Ϣ";   
  }
  catch(ex)
  {
    myAlert("��ʼ��ʱ����:"+ex);
  }
}
 
//��ʼ����ͬ���ֱ���Ϣ 
function initFactorGrid()
{
	  var contno=fm.RIContNo.value;
    var iArray = new Array();
    try
    {
		iArray[0]=new Array();                                                                  
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п� 
		iArray[0][2]=100;            			  //�����ֵ 
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array(); 
		iArray[1][0]="Ҫ������"; 
		iArray[1][1]="38px"; 
		iArray[1][2]=200; 
		iArray[1][3]=2; 
		iArray[1][4]="lrfactor"; 
		iArray[1][5]="1|2"; 	 							//�����ô���ֱ���ڵ�1��2 
		iArray[1][6]="1|0";
    iArray[1][15]=""+contno;
		iArray[1][17]="2"; 
		iArray[1][19]=1;
		                                                                                        
		iArray[2]=new Array();  
		iArray[2][0]="Ҫ�ش���";
		iArray[2][1]="25px";
		iArray[2][2]=200;
		iArray[2][3]=3;
		                                                                                       
		iArray[3]=new Array();                                                                 
		iArray[3][0]="Ҫ��ֵ";                                                                 
		iArray[3][1]="15px";                                                                   
		iArray[3][2]=200;                                                                      
		iArray[3][3]=1;                                                                        
		                                                                                       
	  FactorGrid = new MulLineEnter( "fm" , "FactorGrid" );                                  
	  //��Щ���Ա�����loadMulLineǰ                                                          
	  FactorGrid.mulLineCount = 0;                                                           
	  FactorGrid.displayTitle = 1;                                                           
	  FactorGrid.locked = 0;                                                                 
	  FactorGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��                                     
	  //FactorGrid.selBoxEventFuncName ="CardInfoGridClick"; //��Ӧ�ĺ���������������        
	  FactorGrid.hiddenPlus=0;                                                               
	  FactorGrid.hiddenSubtraction=0;                                                        
	  FactorGrid.loadMulLine(iArray);                                                        
	  	                                                                                     
    }                                                                                      
    catch(ex)                                                                              
    {                                                                                      
        myAlert(ex);                                                                         
    }                                                                                      
}        

</script>                                                                                  