<%@include file="/i18n/language.jsp"%>
 <%
//�������ڣ�2008-08-18
//������  �����  
%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>


<%
	GlobalInput globalInput = (GlobalInput)session.getAttribute("GI");
	
	if(globalInput == null) 
	{
		out.println(""+"��ҳ��ʱ�������µ�¼"+"");
		return;
	}
	String strOperator = globalInput.Operator;
	String tArithmeticDefID = request.getParameter("ArithmeticID");
%>  
                       
<script type="text/javascript">


function initForm()
{
	try
	{
		fm.ArithmeticDefID.value = <%=tArithmeticDefID%>
		fm.NEWArithmeticDefID.value = <%=tArithmeticDefID%>
		//initCalItemTypeGrid();
		initKResultGrid();
		//var strSQL="select distinct(a.arithmeticid),a.arithmeticdefid,(select l.codename from ldcode l where l.code=a.ArithmeticType and l.codetype='Arithmetic'),a.ArithmeticType from riitemcal a  where 1=1 "
		//	+getWherePart('arithmeticdefid','ArithmeticDefID');
		//turnPage1.queryModal(strSQL,CalItemTypeGrid);
	}
	catch(re)
	{
	  myAlert("��RIItemCalInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}
/*
function initCalItemTypeGrid()
{     
                             
	var iArray = new Array();
      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="��ϸ�㷨����";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[2]=new Array();
      iArray[2][0]="�㷨����";      	   		//����
      iArray[2][1]="50px";            			//�п�
      iArray[2][2]=10;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[3]=new Array();
      iArray[3][0]="�㷨��������";      	   		//����
      iArray[3][1]="50px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[4]=new Array();
      iArray[4][0]="�㷨���ͱ���";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
                 
      iArray[5]=new Array();
      iArray[5][0]="ҵ�����ͱ���";      	   		//����
      iArray[5][1]="50px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      
      CalItemTypeGrid = new MulLineEnter( "fm" , "CalItemTypeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //ResultGrid.mulLineCount = 1;   
      CalItemTypeGrid.displayTitle = 1;
      CalItemTypeGrid.locked = 1;
      CalItemTypeGrid.canSel = 1;
      CalItemTypeGrid.canChk = 0;
      CalItemTypeGrid.hiddenSubtraction = 1;
      CalItemTypeGrid.hiddenPlus = 1;
      CalItemTypeGrid.loadMulLine(iArray);  
      CalItemTypeGrid.selBoxEventFuncName ="getDetail"; //��Ӧ�ĺ���������������        
      

	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
function initKResultGrid()
{     
                             
	var iArray = new Array();
      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�㷨����";         		//����
      iArray[1][1]="60px";            		//�п�
      iArray[1][2]=60;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��ϸ�㷨����";      	   		//����
      iArray[2][1]="60px";            			//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[3]=new Array();
      iArray[3][0]="��ϸ�㷨����";      	   		//����
      iArray[3][1]="80px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="��ϸ�㷨����";      	   		//����
      iArray[4][1]="50px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[5]=new Array();
      iArray[5][0]="ҵ������";      	   		//����
      iArray[5][1]="50px";            			//�п�
      iArray[5][2]=10;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="����������";      	   		//����
      iArray[6][1]="50px";            			//�п�
      iArray[6][2]=10;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�㷨����";      	   		//����
      iArray[7][1]="100px";            			//�п�
      iArray[7][2]=10;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[8]=new Array();
      iArray[8][0]="�㷨����";      	   		//����
      iArray[8][1]="100px";            			//�п�
      iArray[8][2]=10;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="�㷨����";      	   		//����
      iArray[9][1]="100px";            			//�п�
      iArray[9][2]=10;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="���������";      	   		//����
      iArray[10][1]="100px";            			//�п�
      iArray[10][2]=10;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="���������";      	   		//����
      iArray[11][1]="50px";            			//�п�
      iArray[11][2]=10;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      KResultGrid = new MulLineEnter( "fm" , "KResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      //ResultGrid.mulLineCount = 1;   
      KResultGrid.displayTitle = 1;
      KResultGrid.locked = 1;
      KResultGrid.canSel = 1;
      KResultGrid.canChk = 0;
      KResultGrid.hiddenSubtraction = 1;
      KResultGrid.hiddenPlus = 1;
      KResultGrid.selBoxEventFuncName ="queryClick";
      KResultGrid.loadMulLine(iArray);    

	}
	catch(ex)
	{
		myAlert(ex);
	}
}
</script>
