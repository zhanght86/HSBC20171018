<%
//���컯�˱�  Ʒ�ʲ��컯ά��
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>

<script language="JavaScript">
//���ذ�ť��ʼ��
var str = "";
function initDisplayButton() {
	if (tDisplay=="1") {
		fm.Return.style.display='';
	} else if (tDisplay=="0") {
		fm.Return.style.display='none';
	}
}
// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 

  try {                                   
	// ������ѯ����
    document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
    document.all('AgentName').value = '';
    document.all('UWClass').value = '';
    document.all('UWClassName').value = '';
    document.all('UWLevel').value = '';
    document.all('UWLevelName').value = '';
  } catch(ex) {
    alert("��QualityDifferentiatedInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox() {  
  try {

  }
  catch(ex) {
    alert("��PrtReplaceInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm() {
	try {
		initInpBox();
    	initSelBox();    
		initPolGrid();
  	} catch(re) {
    	alert("PrtReplaceInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  	}
}

// ������Ϣ�б�ĳ�ʼ��
function initPolGrid() {                               
	var iArray = new Array();
      
	try {
		iArray[0]=new Array();
		iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		iArray[0][1]="30px";            		//�п�
		iArray[0][2]=10;            			//�����ֵ
		iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[1]=new Array();
		iArray[1][0]="�������";         		//����
		iArray[1][1]="80px";            		//�п�
		iArray[1][2]=100;            			//�����ֵ
		iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			      
		iArray[2]=new Array();
		iArray[2][0]="�������";         		//����
		iArray[2][1]="80px";            		//�п�
		iArray[2][2]=100;            			//�����ֵ
		iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
			      
		iArray[3]=new Array();
		iArray[3][0]="ҵ��Ա����";         		//����
		iArray[3][1]="100px";            		//�п�
		iArray[3][2]=200;            			//�����ֵ
		iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		iArray[4]=new Array();
		iArray[4][0]="ҵ��Ա���";         		//����
		iArray[4][1]="80px";            		//�п�
		iArray[4][2]=200;            			//�����ֵ
		iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

	    iArray[5]=new Array();
	    iArray[5][0]="ҵ��Ա����";         		//����
	    iArray[5][1]="70px";            		//�п�
	    iArray[5][2]=100;            			//�����ֵ
	    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	    iArray[6]=new Array();
	    iArray[6][0]="���컯����";         		//����
	    iArray[6][1]="80px";            		//�п�
	    iArray[6][2]=100;            			//�����ֵ
	    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	    iArray[7]=new Array();
	    iArray[7][0]="ά��ʱ��";         		//����
	    iArray[7][1]="150px";            		//�п�
	    iArray[7][2]=100;            			//�����ֵ
	    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      
	    PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
	    //��Щ���Ա�����loadMulLineǰ
	    PolGrid.mulLineCount = 5;   
	    PolGrid.displayTitle = 1;
	    PolGrid.locked = 1;
	    PolGrid.canSel = 0;
	    PolGrid.hiddenPlus = 1;
	    PolGrid.hiddenSubtraction = 1;
	    //PolGrid.selBoxEventFuncName="ShowPrt";
	    PolGrid.loadMulLine(iArray);  
	      
	    //��Щ����������loadMulLine����
	    //PolGrid.setRowColData(1,1,"asdf");
	} catch(ex) {
    	alert(ex);
    }
}

</script>
