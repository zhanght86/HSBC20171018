
<%
	//**************************************************************************************************
	//Name��StandPayInfoListInit.jsp
	//Function������__Ԥ�����__��ʼ��ҳ��
	//Author��pd
	//Date: 2005-11-1
	//Desc:
	//**************************************************************************************************
%>
<%
	String CaseNo = request.getParameter("CaseNo");
	String customerName = request.getParameter("customerName");
	String customerNo = request.getParameter("customerNo");
	String GrpContNo = request.getParameter("GrpContNo");
	String RiskCode = request.getParameter("RiskCode");
%>
<script language="JavaScript" type="text/javascript">
var turnPage = new turnPageClass();

/**
 *FUNCTION:initForm()
 *DESC :��ʼ��__FORM__��
 */
function initForm()
{
  try
  {
  fm.CaseNo.value="<%=CaseNo%>";
  fm.customerName.value="<%=customerName%>";
  fm.customerNo.value="<%=customerNo%>";
  fm.GrpContNo.value="<%=GrpContNo%>"; 
  fm.RiskCode.value="<%=RiskCode%>"; 
  
  initAffixGrid();   //��ʼ��__Ԥ�����__GRID
  initList();
  }
  catch(ex)
  {
  alert("StandPayInfoListInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+ ex.message);
  }
}

/**
 *FUNCTION:initAffixGrid()
 *DESC :��ʼ��__Ԥ�����__GRID
 */
function initAffixGrid()
{
  var iArray = new Array();
    try
    {
    var sql="";
    if(fm.RiskCode.value!=null &&  fm.RiskCode.value!=""){
      sql=" and riskcode=#" + "<%=RiskCode%>" + "# ";
    }
    
     iArray[0]=new Array("���","30px","0",1);
     
     iArray[1]=new Array();
     iArray[1][0]="���ִ���";     //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
     iArray[1][1]="50px";       //�п�
     iArray[1][2]=10;           //�����ֵ
     iArray[1][3]=2;            //�Ƿ���������,1��ʾ����0��ʾ������
     iArray[1][4]="grpriskcode"
     iArray[1][5]="1|2";                 //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
     iArray[1][6]="0|1";
     iArray[1][9]="���ִ���|NOTNULL";
     iArray[1][15]="1";								//�ؼ�����
     iArray[1][16]=" 1 " + sql + " and riskcode in (select riskcode from lcgrppol where 1=1 and GrpContNo = #"+"<%=GrpContNo%>"+"#)";     //�ÿؼ���ֵ
     //iArray[1][17]="1"
	 //alert(iArray[1][16]);

     iArray[2]=new Array("��������","180px","20",0);
     
     iArray[3]=new Array();
	      iArray[3][0]="����";      	   		//����
	      iArray[3][1]="80px";            			//�п�
	      iArray[3][2]=20;            			//�����ֵ
	      iArray[3][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	      iArray[3][4]="currency";
	      iArray[3][5]="3";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
	      iArray[3][6]="0";              	        //��������з������ô����еڼ�λֵ
	      iArray[3][9]="����|code:currency&NOTNULL";
     
     iArray[4]=new Array();
     iArray[4][0]="Ԥ�����";     //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
     iArray[4][1]="100px";      //�п�
     iArray[4][2]=20;           //�����ֵ
     iArray[4][3]=7;            //�Ƿ���������,1��ʾ����0��ʾ������
     iArray[4][9]="Ԥ�����|NOTNULL&NUM";
     iArray[4][22]="col3"; //�ɱ��־���
     
     //iArray[3]=new Array("Ԥ�����","100px","20",1);

     AffixGrid = new MulLineEnter( "fm" , "AffixGrid" );
     AffixGrid.mulLineCount = 0;
     AffixGrid.displayTitle = 1;
     AffixGrid.canChk = 0;
     AffixGrid.hiddenPlus=0;
     AffixGrid.hiddenSubtraction=0;
     AffixGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
  alert("CaseAffixListInit.jsp-->initAffixGrid�����з����쳣:��ʼ���������!"+ ex.message);
    }
  }


</script>
