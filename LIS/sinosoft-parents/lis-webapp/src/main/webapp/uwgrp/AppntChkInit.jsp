<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AppntChkInit.jsp
//�����ܣ�Ͷ���˲���
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	//alert(11);
	fm.all('ProposalNo').value = tContNo;
	//alert(12);
	fm.all('Flag').value = tFlag;
	//alert(13);
	if(tFlag=="I")
	{
	//alert(14);
	  fm.all('InsuredNo').value =tInsuredNo;
	}   
}

// ������ĳ�ʼ��
function initSelBox(){  
}                                        

function initForm()
{
  try 
  {

    //alert(1);
    initInpBox();
    //alert(2);
    initSelBox();
    //alert(3);    
    initPolGrid();
    //alert(4);
    initOPolGrid();
    
//    alert(LoadFlag);

    if(LoadFlag=="5"){
      fm.all('button1').style.display="none";    
      divCustomerUnionInfo.style.display = "none";	
    }
    if(LoadFlag=="25"){
      fm.all('button1').style.display="";    	
    }
    if(LoadFlag == "3")
    {
   
    divBtCustomerUnionSendIssue.style.display = '';
    divBtCustomerUnion.style.display = "none";
    }
    //alert(5);
    initQuery();

    //divCustomerUnion.style.display = "none";
  }
  catch(re) {
    alert("AppntChkInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var OPolGrid;
// ������Ϣ�б�ĳ�ʼ��
function initOPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ���";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="֤������";         		//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="֤������";         		//����
      iArray[4][1]="200px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����֤������";         		//����
      iArray[5][1]="200px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����֤������";         		//����
      iArray[6][1]="200px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 0;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}



var PolGrid;
// ������Ϣ�б�ĳ�ʼ��
function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ���";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="֤������";         		//����
      iArray[3][1]="180px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="֤������";         		//����
      iArray[4][1]="200px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����֤������";         		//����
      iArray[5][1]="200px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="����֤������";         		//����
      iArray[6][1]="200px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 1;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.selBoxEventFuncName="customerUnion";
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
