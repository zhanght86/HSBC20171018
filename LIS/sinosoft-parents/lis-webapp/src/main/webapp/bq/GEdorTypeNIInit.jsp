<%
//�������ƣ�GEdorTypeNIInput.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initGrpEdor()
{
	document.all('EdorNo').value       = top.opener.document.all('EdorNo').value;
	document.all('GrpContNo').value  	 = top.opener.document.all('ContNoApp').value;
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.all('EdorType').value 		 = top.opener.document.all('EdorType').value;
	document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;	
	document.all('EdorTypeCal').value  = top.opener.document.all('EdorTypeCal').value;
	document.all('ContNo').value 			 = "";
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox() { 
  try 
  {} 
  catch(ex) 
  {}      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {}
  catch(ex)
  { 
  	 alert("��GEdorTypeNIInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

//�����������б�
function initLPInsuredGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          				//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����˴���";        //����
      iArray[1][1]="80px";            	//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����������";        //����
      iArray[2][1]="80px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="���ִ���";         	//����
      iArray[3][1]="80px";            	//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������ͬ������";    //����
      iArray[4][1]="100px";            	//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��������";         	//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      LPInsuredGrid = new MulLineEnter( "fm" , "LPInsuredGrid" ); 
      
      //��Щ���Ա�����loadMulLineǰ
      LPInsuredGrid.mulLineCount = 1;   
      LPInsuredGrid.displayTitle = 1;
	  	LPInsuredGrid.canSel = 1;
      LPInsuredGrid.hiddenPlus = 1;
      LPInsuredGrid.hiddenSubtraction = 1;
      LPInsuredGrid.selBoxEventFuncName = "onInsuredGridSelected";
      LPInsuredGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����

    }catch(ex){
    	alert(ex);
    }
}








//================
function initForm() 
{
	initGrpEdor();
	initLPInsuredGrid();
	initQuery();
	QueryEdorInfo();
	//QueryEdorMoney();		
}

</script>
