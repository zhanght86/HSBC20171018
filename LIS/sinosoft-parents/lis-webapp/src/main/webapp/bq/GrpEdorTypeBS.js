//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage1 = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage2 = new turnPageClass(); 
var turnPage = new turnPageClass(); 
var mSwitch = parent.VD.gVSwitch;

function submitForm()
{

  
    	  var tEndDate = fm.EndDate.value;

    	  if(tEndDate ==""||tEndDate == null)
    	  {
    	  	  alert("�������ж�����");
    	  	  return;
    	  }
    	  else
    		{
    			
    			  var tOldEndDate = fm.OldEndDate.value;

						var retCPDate = compareDate(tOldEndDate,tEndDate);
						
						if(retCPDate != "2")
						{
							alert("�ж�����Ӧ�ڵ�ǰ�������ϴ�Ч���ָ�����֮��!");
							return;
  					}
  					//alert(fm.EdorValidate.value);


				    	  var mOperator = "INSERT";
				    	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
							  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
							  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
								
								var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
								var iWidth=550;      //�������ڵĿ��; 
								var iHeight=250;     //�������ڵĸ߶�; 
								var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
								var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
								showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

								showInfo.focus();
								
							  fm.action ="./GrpEdorTypeBSSave.jsp?Operator="+mOperator;
							  fm.submit(); 
					 
			  }
		
}

function returnParent()
{
	top.opener.getEdorItemGrid();
	top.close();
}
//��ʾ�ύ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	//var rowLine = LCGrpPolGrid.getSelNo();
	queryClick();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showInfo = showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
}

//��ѯ��ť
function queryClick()
{
	var tGrpContNo = fm.GrpContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tEdorType = fm.EdorType.value;
	var strSql = "select c.grpcontno,c.grppolno ,c.riskcode ,c.grpname,c.cvalidate,c.payenddate ,c.peoples2,c.GrpProposalNo,(select startdate from LPGrpContState where 1 = 1 and grppolno = c.grppolno and grpcontno = c.grpcontno and edorno = '"+fm.EdorNo.value+"' and enddate is null and statetype = 'Available' and state = '1')"
	    +" from lcgrppol c where c.grpcontno = '"+tGrpContNo+"' and c.riskcode in (select riskcode from lmriskedoritem where edorcode = '"+fm.EdorType.value+"')";

	turnPage1.queryModal(strSql, LCGrpPolGrid);	 
	var strSQL = "select 1 from lpgrppol p where p.grpcontno = '"+tGrpContNo+"' and p.edorNo = '"+tEdorNo+"' and p.edortype = '"+tEdorType+"'";
	var prr = easyExecSql(strSQL);
	if(prr)
	{
		strSQL = "select min(startdate) from lpgrpcontstate where grpcontno = '"+tGrpContNo+"' and edorno = '"+tEdorNo+"' and edortype = '"+fm.EdorType.value+"' and enddate is null and statetype = 'Available' and state = '1'";
  	var trr = easyExecSql(strSQL);
  	if(trr)
		{
			fm.EndDate.value = trr[0][0];
		}
	}else{
		 fm.EndDate.value = fm.OldEndDate.value;
	}
		
}

	
function QueryEdorInfo()
{
		 var tEdortype=fm.all('EdorType').value;
		 var strSQL = "";
		 if(tEdortype!=null || tEdortype !='')
		 {
		    strSQL="select distinct edorcode, edorname from lmedoritem where edorcode = '" + tEdortype + "'";
	      var arrSelected = new Array();	
				turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
				arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
				if(tEdortype!=null || tEdortype !='')
				{
				    fm.EdorType.value     = arrSelected[0][0];
				    fm.EdorTypeName.value = arrSelected[0][1];
		    }
		    else
				{
					  alert('δ��ѯ����ȫ������Ŀ��Ϣ��');
				}
     }
	   else
		 {
			   alert('��ȫ������Ŀ��ϢΪ�գ�');
		 }
}

function GrpPolSel()
{
	var tGrpContNo = fm.GrpContNo.value;
	var tEdorNo = fm.EdorNo.value;
	var tEdorType = fm.EdorType.value;
	var strSql = "select c.grpcontno,c.grppolno ,decode(c.state,'0','��Ч','1','ʧЧ','δ֪') ,c.startdate,c.enddate "
	    +" from lcgrpcontstate c where c.grpcontno = '"+tGrpContNo+"' order by grppolno,startdate";

	turnPage2.queryModal(strSql, LCGrpContStateGrid);	 
}