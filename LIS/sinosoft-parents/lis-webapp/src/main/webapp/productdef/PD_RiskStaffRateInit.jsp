<%@include file="../i18n/language.jsp"%>
 <%@page contentType="text/html;charset=GBK" %>
<%
//�������ƣ�LABKRateToRiskInit.jsp
//�����ܣ�
//�������ڣ� 
//������  �� 
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script type="text/javascript">

// �����ĳ�ʼ��������¼���֣�

function initInpBox(){  
   fm.all('BranchType').value ='<%=request.getParameter("BranchType")%>';
}

function initForm(){
	try{
		initInpBox();    
		initRateGrid();
		isshowbutton();
	}catch(re){
		myAlert(""+"��ʼ���������!");
	}
}

var RateGrid; 
// ������Ϣ�б�ĳ�ʼ��
function initRateGrid(){                               
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
		
		iArray[1]=new Array();
		iArray[1][0]="�������";         		//����
		iArray[1][1]="60px";            		//�п�100
		iArray[1][2]=200;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[2]=new Array();
		iArray[2][0]="���л���";         		//����
		iArray[2][1]="0px";            		//�п�100
		iArray[2][2]=0;            			//�����ֵ
		iArray[2][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[3]=new Array();
		iArray[3][0]="���ֱ���";         		//����
		iArray[3][1]="60px";            		//�п�60
		iArray[3][2]=100;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="��������"; //����
		iArray[4][1]="100px";        //�п�
		iArray[4][2]=100;            //�����ֵ
		iArray[4][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������    
		    
		iArray[5]=new Array();
		iArray[5][0]="��������(��)"; //����
		iArray[5][1]="80px";        //�п�
		iArray[5][2]=100;            //�����ֵ
		iArray[5][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������    
		
		iArray[6]=new Array();
		iArray[6][0]="��������"; //����
		iArray[6][1]="80px";        //�п�
		iArray[6][2]=100;            //�����ֵ
		iArray[6][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������  
		
		iArray[7]=new Array();
		iArray[7][0]="����ֹ��"; //����
		iArray[7][1]="80px";        //�п�
		iArray[7][2]=100;            //�����ֵ
		iArray[7][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������  
		
		iArray[8]=new Array();
		iArray[8][0]="��������"; //����
		iArray[8][1]="60px";        //�п�
		iArray[8][2]=100;            //�����ֵ
		iArray[8][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������  
		
		iArray[9]=new Array();
		iArray[9][0]="���ִ���"; //����
		iArray[9][1]="0px";        //�п�
		iArray[9][2]=100;            //�����ֵ
		iArray[9][3]=1;              //�Ƿ���������,1��ʾ����,0��ʾ������
		    
		iArray[10]=new Array();
		iArray[10][0]="����"; //����
		iArray[10][1]="60px";        //�п�
		iArray[10][2]=100;            //�����ֵ
		iArray[10][3]=1;              //�Ƿ���������,1��ʾ����,0��ʾ������   
		     
		iArray[11]=new Array();
		iArray[11][0]="�ɷ��������ʹ���"; //����
		iArray[11][1]="0px";        //�п�
		iArray[11][2]=100;            //�����ֵ
		iArray[11][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������    
		
		iArray[12]=new Array();
		iArray[12][0]="�ɷ���������"; //����
		iArray[12][1]="100px";        //�п�
		iArray[12][2]=100;            //�����ֵ
		iArray[12][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������  
		
		
		iArray[13]=new Array();
		iArray[13][0]="�ɷѷ�ʽ����"; //����
		iArray[13][1]="0px";        //�п�
		iArray[13][2]=100;            //�����ֵ
		iArray[13][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������    
		
		iArray[14]=new Array();
		iArray[14][0]="�ɷѷ�ʽ"; //����
		iArray[14][1]="60px";        //�п�
		iArray[14][2]=100;            //�����ֵ
		iArray[14][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������    
		
		iArray[15]=new Array();
		iArray[15][0]="���ϼƻ�����"; //����
		iArray[15][1]="0px";        //�п�
		iArray[15][2]=100;            //�����ֵ
		iArray[15][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������   
		
		iArray[16]=new Array();
		iArray[16][0]="���ϼƻ�"; //����
		iArray[16][1]="60px";        //�п�
		iArray[16][2]=100;            //�����ֵ
		iArray[16][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������   
		
		iArray[17]=new Array();
		iArray[17][0]="�������(��)"; //����
		iArray[17][1]="60px";        //�п�
		iArray[17][2]=100;            //�����ֵ
		iArray[17][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������    
		
		iArray[18]=new Array();
		iArray[18][0]="Ա���ۿ۱���"; //����
		iArray[18][1]="60px";        //�п�
		iArray[18][2]=100;            //�����ֵ
		iArray[18][3]=0;              //�Ƿ���������,1��ʾ����,0��ʾ������   
		
		iArray[19]=new Array();
		iArray[19][0]="��Ч����";         		//����
		iArray[19][1]="100px";            		//�п�60
		iArray[19][2]=100;            			//�����ֵ
		iArray[19][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[20]=new Array();
		iArray[20][0]="���״̬����";         		//����
		iArray[20][1]="0px";            		//�п�60
		iArray[20][2]=100;            			//�����ֵ
		iArray[20][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[21]=new Array();
		iArray[21][0]="���״̬";         		//����
		iArray[21][1]="60px";            		//�п�60
		iArray[21][2]=100;            			//�����ֵ
		iArray[21][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[22]=new Array();
		iArray[22][1]="4px";            		//�п�60
		iArray[22][2]=100;            			//�����ֵ
		iArray[22][3]=3; 
		  
		iArray[23]=new Array();
		iArray[23][1]="4px";            		//�п�60
		iArray[23][2]=100;            			//�����ֵ
		iArray[23][3]=3;           			//�Ƿ���������,1��ʾ����0��ʾ������    
		
		iArray[24]=new Array();
		iArray[24][1]="4px";            		//�п�60
		iArray[24][2]=100;            			//�����ֵ
		iArray[24][3]=3; 
		
		
		iArray[25]=new Array();               //plancode
        iArray[25][0]="PlanCode";         		//����
        iArray[25][1]="60px";            		//�п�60
        iArray[25][2]=100;            			//�����ֵ
        iArray[25][3]=0;        
		RateGrid = new MulLineEnter( "fm" , "RateGrid" ); 
		//��Щ���Ա�����loadMulLineǰ
		RateGrid.mulLineCount = 1;   
		RateGrid.displayTitle = 1;
		RateGrid.locked = 0;
		RateGrid.canSel = 1;
		RateGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		RateGrid.hiddenSubtraction=1; //�Ƿ�����"-"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
		
		//�ڽ��С�+������ʱ���������Զ���ӵ�mulLine�Ķ�Ӧ�ʵ�ֺ���ΪaddRateGrid����
		RateGrid.addEventFuncName="addRateGrid"; 
		RateGrid.selBoxEventFuncName ="selectClick"; 
		RateGrid.loadMulLine(iArray);  
		
		//��Щ����������loadMulLine����
	}
	catch(ex){
		myAlert(ex);
	}
}

</script>
