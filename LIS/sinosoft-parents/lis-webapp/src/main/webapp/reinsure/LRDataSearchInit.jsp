<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2007-2-7
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
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  {
  	
  }
  catch(ex)
  {
    myAlert("��ʼ���������!");
  }
}

// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2��LRGetBsnsDataInit.jsp-->"+"InitSelBox�����з����쳣:��ʼ���������!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initRIDataGrid();
    initRIDataDetailGrid();
  }
  catch(re)
  {
    myAlert("3��LRGetBsnsDataInit.jsp-->"+"��ʼ���������!");
  }
}


// �ӿ�ҵ������
function initRIDataGrid()
{	
	var iArray = new Array();   
  try
  {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="80px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="ҵ������";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;
				
		iArray[3]=new Array();
		iArray[3][0]="�����˿ͻ���";
		iArray[3][1]="80px";
		iArray[3][2]=85;
		iArray[3][3]=0;
				
		iArray[4]=new Array();
		iArray[4][0]="����������"; 
		iArray[4][1]="80px"; 
		iArray[4][2]=85;
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="�ٱ���ͬ���";
		iArray[5][1]="100px";            		 
		iArray[5][2]=85;            			   
		iArray[5][3]=0;
				
		iArray[6]=new Array();
		iArray[6][0]="�ٱ��������";
		iArray[6][1]="100px";            		 
		iArray[6][2]=85;            			   
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="�ۼƷ��ձ��";
		iArray[7][1]="100px";            		 
		iArray[7][2]=85;            			   
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="��ǰ���ձ���";
		iArray[8][1]="80px";
		iArray[8][2]=85;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="���ձ���仯��";
		iArray[9][1]="80px";
		iArray[9][2]=85;
		iArray[9][3]=0;
						
		iArray[10]=new Array();
		iArray[10][0]="�ۼƷ��ձ���";
		iArray[10][1]="80px";
		iArray[10][2]=85;
		iArray[10][3]=0;

		iArray[11]=new Array();
		iArray[11][0]="�����";
		iArray[11][1]="80px";
		iArray[11][2]=85;
		iArray[11][3]=0;
		
		iArray[12]=new Array();
		iArray[12][0]="ҵ������";
		iArray[12][1]="80px";
		iArray[12][2]=85;
		iArray[12][3]=0;

		iArray[13]=new Array();
		iArray[13][0]="EventNo";
		iArray[13][1]="80px";
		iArray[13][2]=85;
		iArray[13][3]=3;
						
		RIDataGrid = new MulLineEnter( "fm" , "RIDataGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		RIDataGrid.mulLineCount = 0;   
		RIDataGrid.displayTitle = 1;
		RIDataGrid.locked = 0;
		RIDataGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		RIDataGrid.selBoxEventFuncName ="queryDetail"; //��Ӧ�ĺ���������������   
		RIDataGrid.hiddenPlus=1;
		RIDataGrid.hiddenSubtraction=1;
		RIDataGrid.loadMulLine(iArray); 
  }
  catch(ex)
  {
      myAlert(ex);
  }
}

// �ֱ���ϸ��Ϣ
function initRIDataDetailGrid()
{	
	var iArray = new Array();   
  try
  {
		iArray[0]=new Array();
		iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=40;            			  //�����ֵ
		iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������   			     
		
		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="80px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���ֱ���";
		iArray[2][1]="80px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ֱ���˾����";
		iArray[3][1]="80px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�ֱ���˾����";
		iArray[4][1]="80px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="�ֱ�����";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;
						
		iArray[6]=new Array();
		iArray[6][0]="�ֱ�����";
		iArray[6][1]="80px";
		iArray[6][2]=85;
		iArray[6][3]=0;
				
		iArray[7]=new Array();
		iArray[7][0]="�ֱ�����"; 
		iArray[7][1]="80px"; 
		iArray[7][2]=85;
		iArray[7][3]=0; 

		iArray[8]=new Array();
		iArray[8][0]="�ֱ�����";
		iArray[8][1]="100px";            		 
		iArray[8][2]=85;            			   
		iArray[8][3]=0;
				
		iArray[9]=new Array();
		iArray[9][0]="�ֱ�Ӷ��";
		iArray[9][1]="100px";            		 
		iArray[9][2]=85;            			   
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="����̯�ؽ��";
		iArray[10][1]="80px";
		iArray[10][2]=85;
		iArray[10][3]=0;
				
		iArray[11]=new Array();
		iArray[11][0]="�ֱ�ҵ������";
		iArray[11][1]="80px";
		iArray[11][2]=85;
		iArray[11][3]=0;
		
		RIDataDetailGrid = new MulLineEnter( "fm" , "RIDataDetailGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		RIDataDetailGrid.mulLineCount = 0;   
		RIDataDetailGrid.displayTitle = 1;
		RIDataDetailGrid.locked = 0;
		RIDataDetailGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
		//RIDataGrid.selBoxEventFuncName =""; //��Ӧ�ĺ���������������   
		RIDataDetailGrid.hiddenPlus=1;
		RIDataDetailGrid.hiddenSubtraction=1;
		RIDataDetailGrid.loadMulLine(iArray); 
  }
  catch(ex)
  {
      myAlert(ex);
  }
}
</script>