<%@include file="../i18n/language.jsp"%>

<%@include file="../i18n/language.jsp"%>

<script type="text/javascript">
var tCheckedArray = new Array();
function initForm()
{
	try{
		//lockPage("数据加载中...........");
		
		//初始化险种要素列表
		var lastIndex;
		var i=0;
		$('#DutyColSelDiv').hide();
		$('#PayColSelDiv').hide();
			$('#riskGrid').datagrid({

            title:'险种要素选择',

            //iconCls:'icon-edit',

            //queryParams:{operator:$("#operator").val()} ,

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	RiskCode:jRiskCode
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',

						frozenColumns:[[

	                //{field:'ck',checkbox:true},

	              //  {title:'险种要素编码',field:'riskParamCode',width:100,sortable:true ,align:'center'},
	              //  {title:'险种要素名称',field:'riskParamName',width:100,sortable:true ,align:'center'}

						]],

            columns:[[
            {field:'ck',checkbox:true},
            {title:'序号',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'险种要素编码',field:'riskParamCode',width:100,sortable:true ,align:'center'},
	           {title:'险种要素名称',field:'riskParamName',width:100,sortable:true ,align:'center'},
	           {title:'要素样式预览',field:'riskParamsShow',width:200,sortable:true ,align:'center'},
             {field:'fileterSql',title:'过滤SQL',width:400,align:'center',sortable:true,editor:{type:'text',options: {
 													 ondblclick: function() { myAlert('hello'); }
  												}}
							},
	 		//IE11-JQuery-Display:Width不起作用，增加Hidden属性
             {field:'chooseflag',title:'是否勾选',width:0,align:'center',sortable:true,hidden:true
             	,	formatter:function(value,row,index){
             		//alert(value+":"+row+":"+index);
							//if (value==1){
								//alert("index:"+index);
							//$('#riskGrid').datagrid('selectRow', index);
							tCheckedArray[index] = value;
							//alert('1:'+tCheckedArray.length);
							//}
							//else
							//{
							//	alert("index:"+index);
							//}
							return value;
						}
             	}

            ]],
            
        onBeforeEdit:function(index,row){   
        row.editing = true;   
        $('#riskGrid').datagrid('refreshRow', index);   
    },   
    onAfterEdit:function(index,row){   
        row.editing = false;   
        $('#riskGrid').datagrid('refreshRow', index);   
    },   
    onCancelEdit:function(index,row){   
        row.editing = false;   
        $('#riskGrid').datagrid('refreshRow', index);   
    }   ,
    
		onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#riskGrid').datagrid('endEdit', lastIndex);
						$('#riskGrid').datagrid('beginEdit', rowIndex);
					//	$("input.datagrid-editable-input").keyup(function() { $('#riskGrid').datagrid('endEdit', rowIndex); });

					}
					lastIndex = rowIndex;
		}

 

            
 									//	rownumbers:true,
										,singleSelect:false
									//	striped: true,
									//	nowrap: true,
									//	remoteSort: false,
			   			  	//  pagination:false
			   //,	fit:true		  	
			   ,loadMsg:"正在加载数据,请稍候"
			   ,onLoadSuccess: function()
			   {
			   		//$('#riskGrid').datagrid('selectRow', 1);
			   		//alert(tCheckedArray.length);
			   		for(n=0;n<tCheckedArray.length;n++)
			   		{
			   			//alert("tCheckedArray[n]:"+tCheckedArray[n]);
			   			if(tCheckedArray[n]=='1')
			   			{
			   				$('#riskGrid').datagrid('selectRow', n);
			   			}
			   		}
			   		
			   	}			  	
        });
        
        
        //刷新责任和缴费选项
        lockPage("数据加载中...........");
        		//alert('1');
        		//alert(jStandbyFlag1);
  			jQuery.post(
  			"./initAllRiskParams.jsp?Type=DutyPay&RiskCode="+jRiskCode+"&StandbyFlag1="+jStandbyFlag1,
  			//params,
  					function(data) {
							var RuleMapArray = data;
							//alert('RuleMapArray.length:'+RuleMapArray.length);
							try{
									for(i=0;i<RuleMapArray.length;i++)
									{
										var showtype = RuleMapArray[i].showtype;
										//	alert("showtype:"+showtype);
										showtype = showtype+"Choose";
										$('#'+showtype).attr("checked",true);
										//alert("showtype:"+showtype);
										if(showtype=='DutyChoose')
										{
											$('#DutyColSelDiv').show();
										}
										else
										{
											$('#PayColSelDiv').show();
										}
									}		
									unLockPage();
		  				}
		  				catch(ex)
		  				{
		  					//alert(ex);
		  				}
					},"json" 
				);
	
	$('#TeacherDiv').tabs({ 
			onSelect:function(title){ 
				 refresh_tab();
	} 
});

	//unLockPage();
	//初始化责任列表
	jQuery.post(
  			"./initDutyPayColSel.jsp?Type=Duty&RiskCode="+jRiskCode,
  			//params,
  					function(data) {
							var RuleMapArray = data;
						//	alert('RuleMapArray.length:'+RuleMapArray.length);
							try{
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										var tDutyPayFlag = "";
										
										if(detail!=null&&detail!='')
										{
											if(tType=='Duty')
											{
												$('div.DutyGridDiv').html(detail);
											}
											
											if(tType=='Pay')
											{
												$('div.PayGridDiv').html(detail);
											}
										}
									}		
									unLockPage();
		  				}
		  				catch(ex)
		  				{
		  					//alert(ex+"./initDutyPayColSel.jsp?Type=Duty&RiskCode="+jRiskCode);
		  				}
					},"json" 
				);
	//初始化缴费列表
	}catch(re){
		//alert("HTPeopServManaInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}



</script>
