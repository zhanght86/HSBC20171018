<%@include file="/i18n/language.jsp"%>
<%
//Creator :�ű�
//Date :2006-08-21
%>
<!--�û�У����-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.utility.*"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

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
	var x=0;
	var y=0;
	var com=new Array(y);
	var line=new Array(x);
	
function initInpBox()
{
  try
  {  
    fm.all('OperateNo').value = <%=request.getParameter("OperateNo")%>;   
    fm.all('CodeType').value = <%=request.getParameter("CodeType")%>;
    fm.all('SerialNo').value = <%=request.getParameter("SerialNo")%>;
    fm.all('RIPolno').value = <%=request.getParameter("RIPolno")%>;

  }         
  catch(ex) 
  {
    myAlert("���г�ʼ���ǳ��ִ��󣡣�����");
  }
}

// �����б�ĳ�ʼ��
function initSelBox()
{
	try
	{
		fm.RelaTempPolBut.style.display='';	  	
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
        intPreceptSearchGrid();  
    	intPreceptInfoGrid(); 
		queryClick();
    
  	}
  	catch(re)
	{
		myAlert("ReContManageInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}

//�ٱ�������ѯ mulline��ʼֵ�趨
function intPreceptSearchGrid(){
	var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1]="30px";            		//�п�
			iArray[0][2]=40;            			  //�����ֵ
			iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
			
			iArray[1]=new Array();
			iArray[1][0]="�ٱ���ͬ����";
			iArray[1][1]="90px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;
			
			iArray[2]=new Array();
			iArray[2][0]="�ٱ���������";
			iArray[2][1]="90px";  
			iArray[2][2]=100;     
			iArray[2][3]=0;     
			
			iArray[3]=new Array();
			iArray[3][0]="�ٱ���������";
			iArray[3][1]="100px"; 
			iArray[3][2]=100;
			iArray[3][3]=0;
			
			iArray[4]=new Array();
			iArray[4][0]="�ۼƷ�������";
			iArray[4][1]="80px";
			iArray[4][2]=100;
			iArray[4][3]=0;
			
			iArray[5]=new Array();
			iArray[5][0]="�ۼƷ�������";
			iArray[5][1]="80px";
			iArray[5][2]=100;
			iArray[5][3]=0;
			
			iArray[6]=new Array();
			iArray[6][0]="�ֱ��������ʹ���"; //01����ͬ�ֱ���02����ʱ�ֱ�
			iArray[6][1]="0px";  
			iArray[6][2]=100;     
			iArray[6][3]=3;
			
			iArray[7]=new Array();
			iArray[7][0]="�ֱ���������";
			iArray[7][1]="80px";  
			iArray[7][2]=100;     
			iArray[7][3]=0;
	
			iArray[8]=new Array();
			iArray[8][0]="����״̬";
			iArray[8][1]="80px";  
			iArray[8][2]=100;     
			iArray[8][3]=0;
			
			iArray[9]=new Array();
			iArray[9][0]="����״̬����"; //01 ��Ч  02 δ��Ч  
			iArray[9][1]="0px";  
			iArray[9][2]=100;     
			iArray[9][3]=3;
			
	  	PreceptSearchGrid = new MulLineEnter( "fm" , "PreceptSearchGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ
	  	PreceptSearchGrid.mulLineCount = 10;   
	  	PreceptSearchGrid.displayTitle = 1;
	  	PreceptSearchGrid.locked = 0;
	  	PreceptSearchGrid.canSel =1; // 1 ��ʾ ��0 ���أ�ȱʡֵ��
	  	PreceptSearchGrid.selBoxEventFuncName ="showPrecept"; //��Ӧ�ĺ���������������   
	  	PreceptSearchGrid.hiddenPlus=1; 
	  	PreceptSearchGrid.hiddenSubtraction=1;
	  	PreceptSearchGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
//�ٱ�������ѯ mulline��ʼֵ�趨
function intPreceptInfoGrid(){
	var iArray = new Array();   
    try
    {
			iArray[0]=new Array();
			iArray[0][0]="���";         			  //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
			iArray[0][1]="30px";            		//�п�
			iArray[0][2]=40;            			  //�����ֵ
			iArray[0][3]=0;              			  //�Ƿ���������,1��ʾ����0��ʾ������
			
			iArray[1]=new Array();
			iArray[1][0]="��������";
			iArray[1][1]="90px";  
			iArray[1][2]=100;     
			iArray[1][3]=0;
			
			iArray[2]=new Array();
			iArray[2][0]="�ٱ���˾";
			iArray[2][1]="90px";  
			iArray[2][2]=100;     
			iArray[2][3]=0;     
			
			iArray[3]=new Array();
			iArray[3][0]="�ٷ��޶�";
			iArray[3][1]="0px"; 
			iArray[3][2]=100;
			iArray[3][3]=3;
		
			iArray[4]=new Array();
			iArray[4][0]="�ٷֱ���";
			iArray[4][1]="80px";
			iArray[4][2]=100;
			iArray[4][3]=0;

			iArray[5]=new Array();
			iArray[5][0]="���ʱ�";
			iArray[5][1]="80px";
			iArray[5][2]=100;
			iArray[5][3]=0;
			
			iArray[6]=new Array();
			iArray[6][0]="Ӷ���ʱ�";
			iArray[6][1]="80px";
			iArray[6][2]=100;
			iArray[6][3]=0;
			
						
	  	PreceptInfoGrid = new MulLineEnter( "fm" , "PreceptInfoGrid" ); 
	  	//��Щ���Ա�����loadMulLineǰ   
	  	PreceptInfoGrid.displayTitle = 1;
	  	PreceptInfoGrid.locked = 0;
	  	PreceptInfoGrid.canSel =0; // 1 ��ʾ ��0 ���أ�ȱʡֵ��  
	  	PreceptInfoGrid.hiddenPlus=1; 
	  	PreceptInfoGrid.hiddenSubtraction=1;
	  	PreceptInfoGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
</script>


