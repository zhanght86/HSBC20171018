

//�������ƣ�PD_LMDutyGetClmCal.js
//�����ܣ����θ����⸶������㹫ʽ
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function initAllInput()
{
	//alert('222');
	       lockPage(""+"���ݼ�����..........."+"");
        		//alert('1');
  			jQuery.post(
  			"./initAllRiskParams.jsp?Type=Input&RiskCode="+jRiskCode+"&StandbyFlag1="+jStandbyFlag1,
  			//params,
  					function(data) {
							var RuleMapArray = data;
							//alert('RuleMapArray.length:'+RuleMapArray.length);
							try{
								  $('#DivLCRiskPay').hide();
								  $('#DivLCRiskDuty').hide();
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										var tDutyPayFlag = "";
										
										if(detail!=null&&detail!='')
										{
												//alert('tType'+tType);
											//prompt('',detail);
											if(tType=='Risk')
											{
												//if(detail==null||detail=='')
												//{
												//	detail = "<div>����ѡ������Ҫ��</div>";
												//}
												//$(document.body).append(detail);
												//divRisk
												//alert($('div.divRisk'));
												$('div.divRisk').html(detail);
												$(document.body).append("<hr>");
											}
											else if(tType=='Pay')
											{
												 $('#DivLCRiskPay').show();
												initPayForm();
												
											}
											else if(tType=='Duty')
											{
												initDutyForm();
												 $('#DivLCRiskDuty').show();
											}
											
										}
										
									}		
									var tbutton = "<div id=\"inputdetail\" class=\"demo-description\" style=\"display: " + ( "query"==contOpt ? "none" : "" )  + "\"><input type='button' class=common  value='"+"��"+"  "+"��"+"' onclick='saveRiskFace();'></div>";

									$(document.body).append(tbutton);
									$( "#ulRisk" ).sortable({
											revert: true});
	
									$( "ul, li" ).disableSelection();
		
									unLockPage();
									
		  				}
		  				catch(ex)
		  				{
		  					myAlert(ex);
		  				}
					},"json" 
				);
}



	function saveRiskFace()
	{
		var riskOrder = "";
		var ul = document.getElementById('ulRisk');
		var lis = ul.getElementsByTagName("li");
		for(i = 0 ; i < lis.length ; i++){
  		//alert(lis[i].id+":"+lis[i].innerHTML);
  		riskOrder = riskOrder  + lis[i].id + "#";
    }
	//	alert(riskOrder);
		
			lockPage(""+"���ݱ�����..........."+"");
	   $.ajax({
            type:'POST',
            url:'showRiskFaceSave.jsp',
            data:'riskOrder='+riskOrder+'&RiskCode='+jRiskCode+'&StandbyFlag1='+jStandbyFlag1,
            success:function(data){
            	//alert(data.trim());
            	unLockPage();
            	myAlert(""+"�������"+"");
            	parent.refresh_tab();
            	return true;
                if(data){
                 
                }else{
                }
            }
        });
        
	}
	
	//��ʾ�ɷѺ�����ѡ��
	
	function initPayForm()
{
	try{
		//�޸ĳ�ʼ����ʽ
			//��ʼ�������б�
		jQuery.post(
  			"./initDutyPayColSel.jsp?Type=InitPay&RiskCode="+jRiskCode,
  			//params,
  					function(data) {
							var RuleMapArray = data;
							try{
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										
										if(detail!=null&&detail!='')
										{
											$('#divPay').append(detail);
											 $("#divPay").height('150px');
											$('#payGrid').datagrid(
											{ fit:true,
												rownumbers:true,
												pagination:false,
 												Height:500, 
												title:''+"���ֽɷ���Ϣ"+'',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'PayGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',
						frozenColumns:[[  
            {field:'ck',checkbox:true}  
        ]]  						
												
												});
										}
									}		
		  				}
		  				catch(ex)
		  				{
		  					myAlert(ex);
		  				}
					},"json" 
				);		
		
		
		
		
		
		
		
		
		/*
		
		
		
			$('#payGrid').datagrid({

            title:'���ֽɷ���Ϣ',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'PayGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',
            columns:[[
            {field:'ck',checkbox:true},
            {title:'���',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'���α���',field:'DutyCode',width:100,sortable:true ,align:'center'},
	           {title:'�ɷѱ���',field:'PayPlanCode',width:100,sortable:true ,align:'center'},
	           {title:'�ɷ�����',field:'PayPlanName',width:200,sortable:true ,align:'center'},
             {title:'����',field:'Prem',width:400,align:'center',sortable:true}
            ]],
            
				loadMsg:"���ڼ�������,���Ժ�"
				,onLoadSuccess: function()
			   {
			   	}			  	
        });
*/
	
	}catch(re){
		//alert(re);
		//alert("HTPeopServManaInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}


	
	function initDutyForm()
{
	try{
			//�޸ĳ�ʼ����ʽ
			//��ʼ�������б�
		jQuery.post(
  			"./initDutyPayColSel.jsp?Type=InitDuty&RiskCode="+jRiskCode,
  			//params,
  					function(data) {
							var RuleMapArray = data;
						//	alert('RuleMapArray.length:'+RuleMapArray.length);
							try{
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										
										if(detail!=null&&detail!='')
										{
											$('#divDuty').append(detail);
											 $("#divDuty").height('150px');
											$('#dutyGrid').datagrid(
											{ fit:true,
												rownumbers:true,
												pagination:false,
 												Height:500, 
												title:''+"����������Ϣ"+'',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'DutyGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',
						frozenColumns:[[  
            {field:'ck',checkbox:true}  
        ]]  						
												
												});
										}
									}		
		  				}
		  				catch(ex)
		  				{
		  					myAlert(ex);
		  				}
					},"json" 
				);		
		
		//alert(tcol);
		/*
		var tcol= [[
            {field:'ck',checkbox:true},
            {title:'���',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'���α���1',field:'DutyCode',width:100,sortable:true ,align:'center'},
	           {title:'��������',field:'DutyName',width:200,sortable:true ,align:'center'},
             {title:'����',field:'Prem',width:400,align:'center',sortable:true}
            ]];
            */
            
            /*
			$('#dutyGrid').datagrid({

            title:'���ֽɷ���Ϣ',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'DutyGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						//idField:'idx',

            columns:[[
            {field:'ck',checkbox:true},
            {title:'���',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'���α���',field:'DutyCode',width:100,sortable:true ,align:'center'},
	           {title:'��������',field:'DutyName',width:200,sortable:true ,align:'center'},
             {title:'����',field:'Prem',width:400,align:'center',sortable:true}
            ]],
        
				loadMsg:"���ڼ�������,���Ժ�"
				,onLoadSuccess: function()
			   {
			   	}			  	
        });
*/
	
	}catch(re){
		//alert(re);
		//alert("HTPeopServManaInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}
