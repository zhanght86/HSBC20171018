<%
//�������ƣ�LPPolQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">
//����ʱ��ѯ
function reportDetailClick(cObj)
{
	  var ex,ey;
	  ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
	  ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
	  divLPPol2.style.left=ex;
	  divLPPol2.style.top =ey;
	  divLPPol2.style.display ='';
}

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {               
		document.all('PolNo').value='';
		document.all('CustomerNo').value='';
		try{
		document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
		document.all('EdorType').value = top.opener.document.all('EdorType').value;
		document.all('GrpPolNo').value = top.opener.document.all('GrpPolNo').value;

		}catch (e)
		{}

  }
  catch(ex)
  {
    alert("��LPPolQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
function initSelBox()
{  
  try                 
  {
  //123
  }
  catch(ex)
  {
    alert("��LPPolQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
//       alert('aaa');                    

    initInpBox();
    initSelBox();  
//      alert('bbb');                    

    initLPPolGrid();
  }
  catch(ex)
  {
    alert("LPPolQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initLPPolGrid()
{
    var iArray = new Array();
      
    try
     {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��             
      iArray[0][1]="30px";         			//�п�                                                     
      iArray[0][2]=10;          			//�����ֵ                                                 
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[1]=new Array();                                                                                       
      iArray[1][0]="������";    			//����                                                     
      iArray[1][1]="150px";            			//�п�                                                     
      iArray[1][2]=100;            			//�����ֵ                                                 
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[2]=new Array();                                                                                       
      iArray[2][0]="Ͷ��������";         		//����                                                     
      iArray[2][1]="150px";            			//�п�                                                     
      iArray[2][2]=100;            			//�����ֵ                                                 
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[3]=new Array();                                                                                       
      iArray[3][0]="�����˿ͻ���";         		//����                                                     
      iArray[3][1]="200px";            			//�п�                                                     
      iArray[3][2]=100;            			//�����ֵ                                                 
      iArray[3][3]=0;             			//�Ƿ���������,1��ʾ����0��ʾ������                      
                                                                                                                   
      iArray[4]=new Array();                                                                                       
      iArray[4][0]="����������";         		//����                                                     
      iArray[4][1]="150px";            			//�п�                                                     
      iArray[4][2]=100;            			//�����ֵ                                                 
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
                                                                                                                   
      iArray[5]=new Array();                                                                                       
      iArray[5][0]="���ֱ���";         		//����                                                     
      iArray[5][1]="100px";            			//�п�                                                     
      iArray[5][2]=100;            			//�����ֵ                                                 
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
                                                                                                                   
      iArray[6]=new Array();                                                                                       
      iArray[6][0]="������";         			//����                                                     
      iArray[6][1]="100px";            			//�п�                                                     
      iArray[6][2]=100;            			//�����ֵ                                                 
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������        
      
      LPPolGrid = new MulLineEnter( "fm" , "LPPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
     
      LPPolGrid.mulLineCount = 10;   
      LPPolGrid.displayTitle = 1;
      LPPolGrid.canSel=1;
      LPPolGrid.loadMulLine(iArray);  
      LPPolGrid.detailInfo="������ʾ��ϸ��Ϣ";
      LPPolGrid.detailClick=reportDetailClick;
      //��Щ����������loadMulLine����
      //SubReportGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>