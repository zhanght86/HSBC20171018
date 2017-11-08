var needD = true;
var length = 2;
var fm = Ext.form;
var sm = new Ext.grid.CheckboxSelectionModel();
var rowId = new Ext.grid.RowNumberer();
var strJson = [ rowId, sm ];
var dataId = new Array();
var data = [ [], [], [], [], [], [], [], [], [], [] ];
var cm = null;
var grid = null;
var ds = null;
var rowDisable=false;
var mTestID=1;

var mdestination = "";
/*var dataType = new Array();

var modDisType = new Array();*/

function displayDicTable(destination,jsonData) {
   mdestination = destination;
	// 隐藏相关按钮
	length = 2;
	strJson = [ rowId, sm ];
	needD = true;
	dataId.length=0;
	getBaseBomItems();
	var width=40;
	for ( var i = 0; i < ColumnHeadArray.length; i++) {
	// 	alert('button:'+TableColumnNameArray[i]+":ColumnHeadArray[i]:"+ColumnHeadArray[i]+":TableColumnNameArray[i]:"+TableColumnNameArray[i]);
	
		var newJson = null;
		width+=200;
		if (ColumnDataTypeArray[i]== "String") {
			
			var editor;
			if(BaseBOMItemSourceArray[i]!="")
			{
				
				var store = new Ext.data.JsonStore({
		            fields: ['code', 'name'],
		            root: "rows",
		            url   : "getComboxData.jsp?sql="+BaseBOMItemSourceArray[i]
		           });
				if(!ColumnMultipleArray[i])
				{
					editor= new Ext.form.ComboBox({
				    	  store: store,
				    	  width:100,
				    	  valueField : "name",
				    	  displayField:'name',
				     	  typeAhead: true,
				     	  mode: 'remote',
				          lazyInit: true,
				          forceSelection: true,
				          triggerAction: 'all',
				          emptyText:'',
				          listClass: 'x-combo-list-small'
				       });
				}
				else
				{
					editor= new Ext.form.MultiComboBox({
						width : 200,
						store : store,
						valueField : "name",
						displayField : "name",
						labelSeparator : '：',
						displaySeparator : ';',
						valueSeparator : ';',
						maxHeight : 100,
						mode : 'remote',
						value : '1,2',
						forceSelection : true,
						hiddenName : 'test',
						editable : true,
						triggerAction : 'all',
						allowBlank : false
					});
				}
			}
			else 
			{
				editor=new fm.TextField({
					   blankText:"",
					   allowBlank:false
				   });
			}
			newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				align :'center',
				editor :editor
			};
		} else if (ColumnDataTypeArray[i]== "Date") {
			newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				align :'center',
				renderer :Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
				editor :new fm.DateField( {
					format :'Y-m-d H:i:s'
				})
			};
		} else if (ColumnDataTypeArray[i] == "Number" || ColumnDataTypeArray[i]== "INT") {
			newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				allowDecimals :false,
				align :'center',
				editor :new fm.NumberField( {
					allowBlank :true,
					allowNegative :false,
					style :'text-align:left'
				})
			};
		}
		 else if (ColumnDataTypeArray[i]== "Button") {
				newJson = {
				header :ColumnHeadArray[i],
				dataIndex :TableColumnNameArray[i],
				width :200,
				align :'center',
				renderer:renderDescn
			};
			
			//data[0][i]='test1111';
		}
		
		strJson[strJson.length] = newJson;
		dataId[i] = {
			name :TableColumnNameArray[i]
		};
	}
	cm = new Ext.grid.ColumnModel(strJson);

	var Plant = Ext.data.Record.create(dataId);
	if(destination=="init")
	{
		var resultReader = new Ext.data.JsonReader({
		                                        totalProperty: "totalProperty",
		                                        root: "rows",
		                                        id  : "id"
		                                    }, Plant);
	   
		   ds = new Ext.data.Store({
			    reader: resultReader,
		        //proxy:  new Ext.data.MemoryProxy(jsonData)
		       proxy:  new Ext.data.PagingMemoryProxy(jsonData)
		    });
	}
	else
	{
		ds=new Ext.data.Store( {
			//proxy :new Ext.data.MemoryProxy(data),
			proxy:  new Ext.data.PagingMemoryProxy(data),
			reader :new Ext.data.ArrayReader( data, dataId)
			//reader :new Ext.data.ArrayReader( {}, dataId)
		});
	}
	
	grid = new Ext.grid.EditorGridPanel( {
		ds :ds,
		cm :cm,
		sm :sm,
		autoHeight :true,
		autoScroll :true,
		stripeRows :true,
		width :width,
		clicksToEdit:1,
		tbar : [ {
			text :'增加一行',
			handler : function()
		{
			if(rowDisable)
			{
				return;
			}
			var recordId={};
			
			var p = new Plant(recordId);
			
			for(var j=0;j<TableColumnNameArray.length;j++)
			{
				if(ColumnDataTypeArray[j]=='Button')
				{
					var RuleID = getOneRuleID(mRuleName);
					p.set(TableColumnNameArray[j],RuleID);
				}
				else
				{
					p.set(TableColumnNameArray[j],"");
				}
			}
			grid.stopEditing();
			ds.insert(0, p);
			grid.reconfigure(ds, cm);
			grid.startEditing(0, 2);
		}
		}, {
			text :'删除选中行',
			handler : function() {
			if(rowDisable)
			{
				return;
			}
			    if(!confirm("确认要删除选中的数据吗？"))
			    {
			    	return ;
			    }
				grid.stopEditing();
				var selctions = sm.getSelections();
				for ( var i = 0; i < selctions.length; i++) {
					ds.remove(selctions[i]);
				}
				grid.reconfigure(ds, cm);
			}
		}, {
			text :'下载数据导入模板',
			handler : function() {
			if(rowDisable)
			{
				return;
			}
			    if(!confirm("确认要删除选中的数据吗？"))
			    {
			    	return ;
			    }
				grid.stopEditing();
				var selctions = sm.getSelections();
				for ( var i = 0; i < selctions.length; i++) {
					ds.remove(selctions[i]);
				}
				grid.reconfigure(ds, cm);
			}
		}, {
			text :'导入数据',
			handler : function() {
			if(rowDisable)
			{
				return;
			}
			    if(!confirm("确认要删除选中的数据吗？"))
			    {
			    	return ;
			    }
				grid.stopEditing();
				var selctions = sm.getSelections();
				for ( var i = 0; i < selctions.length; i++) {
					ds.remove(selctions[i]);
				}
				grid.reconfigure(ds, cm);
			}
		} ],
		 bbar: new Ext.PagingToolbar({
		        pageSize: 10,
		        store: ds,
		        displayInfo: true,
		        displayMsg: '显示第 {0} 条到 {1} 条记录，一共 {2} 条',
		        emptyMsg: "没有记录"
		    })

	});
    ds.load({params:{start:0, limit:10}});
	//ds.load();
	//sm.addListener("rowselect", RowToLogic);
	//grid.addListener("afteredit", CellToLogic);
	//grid.addListener("beforeedit", InitLogic);

	var gridNode = document.getElementById('grid-example');
	while (gridNode.firstChild)
		gridNode.removeChild(gridNode.firstChild);

	grid.render('grid-example');
	
    checkNodesState(flag);
    if(flag==0)
    {
    	disableEditable();
    }
}
function addMsgResult(tKeyID,tEditFlag)
{
	dialogURL="../ibrms/LDMsgResultMain.jsp?KeyID="+tKeyID+"&EditFlag="+tEditFlag;  
	showModalDialog(dialogURL,window,"status:no;help:0;close:0;dialogWidth:1000px;dialogHeight:500px");
}
function renderDescn(value, cellmeta, record, rowIndex, columnIndex, store) {
	
		    //var record.get(dataId[j].name) = 
		    var tvalue = "";
		    for (j = 0, len = dataId.length; j < len; j++) {
				var dad = record.get(dataId[j].name);
					tvalue = tvalue + "," + dad;
			}
			var str = "";
			//alert(mdestination);
				 var tRES_DEFINE = getMapValueByKey('RES_DEFINE',tLanguage)
	 if(tRES_DEFINE==null||tRES_DEFINE=='')
	 {
	 	tRES_DEFINE = "定义提示信息";
	 }
	 
			if(mdestination=="init")
			{
				  str = "<input type='button' value='"+tRES_DEFINE+"' onclick='addMsgResult(\""+mRuleName+value+"\",\"0\")'>";
			}
			else
			{
				 str = "<input type='button' value='"+tRES_DEFINE+"' onclick='addMsgResult(\""+mRuleName+value+"\",\"1\")'>";
			}
		//record.set('RuleId',value);
			/*
        var str = "<input type='button' value='定义提示信息' onclick='addMsgResult(\""+mRuleName+value+"\");alert(\"" +
            "这个单元格的值是：" + value + "\\n" +
            "这个单元格的配置是：{cellId:" + cellmeta.cellId + ",id:" + cellmeta.id + ",css:" + cellmeta.css + "}\\n" +
            "这个单元格对应行的record是：" + record.data["RuleId"] + "，一行的数据都在里边\\n" +
            "这是第" + rowIndex + "行\\n" +
            "这是第" + columnIndex + "列\\n" +
            "RuleName" + mRuleName + "\\n" +
            "test" +tvalue + "列\\n" +
            "这个表格对应的Ext.data.Store在这里：" + store + "，随便用吧。" +
            "\")'>";
        */
        return str; 
}

function disableEditable()
{
	try{
		for(var i=2;i<cm.getColumnCount();i++)
		{
		    cm.setEditable(i,false);
		}
	}catch(e)
	{
        alert("决策表不可编辑属性初始化出错，请不要再决策表中编辑")
		}
	

	}


function checkNodesState(flag)
{
   switch(flag)
   {
   case 0:
   {
	   disableInputNodes();
	   disableSpanNodes();
	   hideButtons();
	   break;
   }
   case 1:
   {
	   disableSpanNodes();
	   enableInputNodes();
	   hideButtons();
	   break;
   }
   case 2:
   {
	   enableSpanNodes();
	   disableInputNodes();
	   showButtons();
	   break;
   }
   case 4:
   {
	   disableSpanNodes();
	   enableInputNodes();
	   hideButtons();
	   break;
   }
   }
}
function prepareStore(destination)
{
	var reStore =null;
	if(destination=="init")
	{
		reStore= new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
		
	}else
	{
		reStore= new Ext.data.Store( {
			proxy :new Ext.data.MemoryProxy(data),
			reader :new Ext.data.ArrayReader( {}, dataId)
		});
		
	}
	
}

function getSelectedDatas() {
	var dataArray = sm.getSelections();
	var reData = new Array();
	if (dataArray.length > 1) {
		alert('您选择的行数大于1，请选择一行！');
	} else if (dataArray.length == 0) {
		alert('您还没有选择行，请选择一行！');

	} else {
		for ( var i = 0; i < dataId.length; i++) {
			var tmp = dataArray[0].get(dataId[i].name);
			if (dataType[i] == 'Date') {
				var dt = new Date(tmp);
				reData[i] = dt.format('Y-m-d H:i:s');
			} else {
				reData[i] = tmp;
			}
		}

	}
	return reData;
}

function getData() {
	getBaseBomItems();
	var data = new Array();
	if (needD) {
		var store = grid.getStore();
		var record = null;
		var message = '';
		for ( var i = 0, length = store.getCount(); i < length; i++) {
			var da = new Array();
			record = store.getAt(i);
			var Integrity = true;
			var rowNull = true;
			for (j = 0, len = dataId.length; j < len; j++) {
				var dad = record.get(dataId[j].name);
				//alert(dataId[j].name+":"+dad+":j:"+j+":len:"+len);
				//alert('j:'+j+'dad:'+dad);
				 if(j==len-1)
				 {
					 if(Integrity)
					 {
					 	//alert(dad+":"+dataId[j].name);
					 	 if(dad==undefined||dad==null||dad=='')
					 	 {
						 da[j]='RuleId';
							}
							else
							{
								da[j]=dad;
							}
						 data[data.length] = da;
						 
						 break;
					 }
					 
				 }
				 else if(j==len-2)
				 {
					 //if(Integrity)
					 {
						 da[j]=dad;
					 }
					 
				 }
				 else
				 {
					 var notNull=false;
					 if(dad!=undefined&&dad!=null)
					 {
						 var val=new String(dad);
						 if(val!="")
						 {
							 notNull=true;
						 }
					 }
					 
					 if (notNull) {
						 
							if (BaseColumnArray[j]) {
								var disData = new Array();
//								alert(dad);
								
								
								
								if(dad.indexOf(";")>-1){
									disData=dad.split(";");
								}else{
									disData[disData.length] = dad;
								}
								
								if(ColumnMultipleArray[j])
								{
									da[j]=";";
								}
								else
								{
									da[j]="";
								}
								
								for ( var k = 0; k < disData.length; k++) {
									var obStr=disData[k];
									var index=obStr.indexOf("-");
											da[j] +=  obStr.substring(0,index);
											if(ColumnMultipleArray[j])
											{
												da[j] +=";" ;
											}
								}
							} else {
								if (ColumnDataTypeArray[j] == 'Date') {
									var dt = new Date(dad);
									da[j] = dt.format('Y-m-d H:i:s');
								} else {
									da[j] = dad;
								}
							}
							rowNull = false;
						} else {
							if (ColumnDataTypeArray[j] != 'Button')
							{
								Integrity = false;
							}
						}
				 }
			 }
			if (Integrity) {
				
			} else if (!rowNull) {
				message += "\n行" + eval(i + 1) + "的数据 不完整！";
			}
		  }
		if (!!message) {
			var discision = confirm(message + "\n如果提交将丢失以上数据");
			if (!discision) {
				return false;
			}
		}
		if (data.length == 0) {
			alert("您在决策表中还没有输入数据,请继续完成!");
			return false;
		}
		return data;
  }else
  {
	  alert("请先生成决策表！");
	  return false;
  }
	
}

Ext.data.PagingMemoryProxy = function(data) {
 Ext.data.PagingMemoryProxy.superclass.constructor.call(this);
 this.data = data;
};

Ext.extend(Ext.data.PagingMemoryProxy, Ext.data.MemoryProxy, {
   load : function(params, reader, callback, scope, arg) {
    params = params || {};
    var result;
    try {
     result = reader.readRecords(this.data);
    } catch (e) {
     this.fireEvent("loadexception", this, arg, null, e);
     callback.call(scope, null, arg, false);
     return;
    }

    // filtering
    if (params.filter !== undefined) {
     result.records = result.records.filter(function(el) {
        if (typeof(el) == "object") {
         var att = params.filterCol || 0;
         return String(el.data[att])
           .match(params.filter)
           ? true
           : false;
        } else {
         return String(el).match(params.filter)
           ? true
           : false;
        }
       });
     result.totalRecords = result.records.length;
    }

    // sorting
    if (params.sort !== undefined) {
     // use integer as params.sort to specify column, since
     // arrays are not named
     // params.sort=0; would also match a array without columns
     var dir = String(params.dir).toUpperCase() == "DESC"
       ? -1
       : 1;
     var fn = function(r1, r2) {
      return r1 < r2;
     };
     result.records.sort(function(a, b) {
        var v = 0;
        if (typeof(a) == "object") {
         v = fn(a.data[params.sort],
           b.data[params.sort])
           * dir;
        } else {
         v = fn(a, b) * dir;
        }
        if (v == 0) {
         v = (a.index < b.index ? -1 : 1);
        }
        return v;
       });
    }

    // paging (use undefined cause start can also be 0 (thus false))
    if (params.start !== undefined && params.limit !== undefined) {
     result.records = result.records.slice(params.start,
       params.start + params.limit);
    }

    callback.call(scope, result, arg, true);
   }
  });
function dataArrayToJson(dataArray)
{
   	var rowCount=dataArray.length;
   	var columnCount=dataArray[0].length;
   	
   	var json=[];
   	
   	for(var i=0;i<rowCount;i++)
   	{
   		json[i]={};
   		for(var j=0;j<columnCount;j++)
   		{
   			json[i][TableColumnNameArray[j]]=dataArray[i][j];
   		}
   	}
   	return json;
}

function initRuleID()
{
		//tongmeng 2011-06-02 init RuleId
		
		for(n=0;n<data.length;n++)
		{
			for(m=0;m<ColumnDataTypeArray.length;m++)
			{
				if(ColumnDataTypeArray[m]=='Button')
				{
					data[n][m] = getOneRuleID(mRuleName);
				}
				
			}
		}
}
