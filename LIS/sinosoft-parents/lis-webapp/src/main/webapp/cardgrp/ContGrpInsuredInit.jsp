<%
//�������ƣ�ContGrpInsuredInit.jsp
//�����ܣ�
//�������ڣ�2004-11-26 11:10:36
//������  �� yuanaq
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
}                                     

function initForm() {
  try {
  	initInpBox();
  	mSwitch = parent.VD.gVSwitch;
  	try{fm.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};     
        try{fm.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};  
  	initPersonInsuredGrid();
  	if(LoadFlag=="4"||LoadFlag=="16"){
        divSaveInsuredButton.style.display="none"; 
        divSaveButton.style.display="";
  	}
  }
  catch(re) {
    alert("ContGrpInsuredInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ��ȡ����Ϣ�б�ĳ�ʼ��
var PersonInsuredGrid;
function initPersonInsuredGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";            	//�п�
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="�������κ�";         		//����
    iArray[1][1]="110px";            		//�п�
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������           

    iArray[2]=new Array();
    iArray[2][0]="ӡˢ��";      //����
    iArray[2][1]="100px";            		//�п�
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[3]=new Array();
    iArray[3][0]="�������˿ͻ���";                //����
    iArray[3][1]="90px";            	//�п�
    iArray[3][3]=2;              	//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="����";         	//����
    iArray[4][1]="60px";            	//�п�
    iArray[4][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
 
    
    iArray[5]=new Array();
    iArray[5][0]="�Ա�";         	//����
    iArray[5][1]="40px";            	//�п�
    iArray[5][3]=2;              	//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[5][4]="Sex"; 
        
    iArray[6]=new Array();
    iArray[6][0]="Ͷ������";         	//����
    iArray[6][1]="60px";            	//�п�
    iArray[6][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[7]=new Array();
    iArray[7][0]="����";         	//����
    iArray[7][1]="80px";            	//�п�
    iArray[7][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
        

    iArray[8]=new Array();
    iArray[8][0]="���ϼƻ�";         	//����
    iArray[8][1]="70px";            	//�п�
    iArray[8][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[9]=new Array();
    iArray[9][0]="����";         	//����
    iArray[9][1]="50px";            	//�п�
    iArray[9][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  

    iArray[10]=new Array();
    iArray[10][0]="����";         	//����
    iArray[10][1]="50px";            	//�п�
    iArray[10][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  

    iArray[11]=new Array();
    iArray[11][0]="ְҵ����";         	//����
    iArray[11][1]="60px";            	//�п�
    iArray[11][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  

    iArray[12]=new Array();
    iArray[12][0]="ְҵ���";         	//����
    iArray[12][1]="60px";            	//�п�
    iArray[12][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  
   
    iArray[13]=new Array();
    iArray[13][0]="ְҵ����";         	//����
    iArray[13][1]="60px";            	//�п�
    iArray[13][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  

    iArray[14]=new Array();
    iArray[14][0]="��������";         	//����
    iArray[14][1]="60px";            	//�п�
    iArray[14][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  

    iArray[15]=new Array();
    iArray[15][0]="��Ч��";         	//����
    iArray[15][1]="80px";            	//�п�
    iArray[15][3]=0;              	//�Ƿ���������,1��ʾ����0��ʾ������  

    iArray[16]=new Array();
    iArray[16][0]="��ͬ��";         	//����
    iArray[16][1]="0px";            	//�п�
    iArray[16][3]=3;              	//�Ƿ���������,1��ʾ����0��ʾ������  
     
    PersonInsuredGrid = new MulLineEnter( "fm" , "PersonInsuredGrid" ); 
    //��Щ���Ա�����loadMulLineǰ
    PersonInsuredGrid.mulLineCount = 0;   
    PersonInsuredGrid.displayTitle = 1;
    PersonInsuredGrid.hiddenPlus = 1;
    PersonInsuredGrid.hiddenSubtraction = 1;
    PersonInsuredGrid.canSel = 1;
    PersonInsuredGrid.selBoxEventFuncName ="getdetail";
    PersonInsuredGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
