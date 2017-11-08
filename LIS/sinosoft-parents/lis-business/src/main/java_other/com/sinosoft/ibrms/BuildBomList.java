package com.sinosoft.ibrms;
import org.apache.log4j.Logger;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sinosoft.ibrms.bom.AbstractBOM;
import com.sinosoft.ibrms.bom.BOMAppnt;
import com.sinosoft.ibrms.bom.BOMCont;
import com.sinosoft.ibrms.bom.BOMInsured;
import com.sinosoft.ibrms.bom.BOMPol;
import com.sinosoft.utility.TransferData;

public class BuildBomList {
private static Logger logger = Logger.getLogger(BuildBomList.class);

	public BuildBomList()
	{
		
	}
	
	private ArrayList mBomList = new ArrayList();
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void initAllParams(TransferData tParamTransferData)
	{
		this.mBomList = new ArrayList();
		int insuredCount = tParamTransferData.getValueByName("insuredCount")==null?0:(Integer)tParamTransferData.getValueByName("insuredCount");
		int polCount = tParamTransferData.getValueByName("polCount")==null?0:(Integer)tParamTransferData.getValueByName("polCount");
		
		BOMCont cont = new BOMCont();
		BOMAppnt tAppnt = new BOMAppnt();
		BOMInsured[] insureds = new  BOMInsured[insuredCount];
		for(int i=0;i<insureds.length;i++)
		{
			BOMInsured insured = new BOMInsured();
			insureds[i] = insured;
			
		}
		BOMPol[] pols = new BOMPol[polCount];
		
		for(int i=0;i<pols.length;i++)
		{
			BOMPol pol = new BOMPol();
			pols[i] = pol;
			
		}
		
		mBomList.add(cont);
		mBomList.add(tAppnt);
		mBomList.add(insureds);
		mBomList.add(pols);	
	}
	
	public boolean replaceBom(Object tBomObj)
	{
		for(int i=0;i<this.mBomList.size();i++)
		{
			Object obj = mBomList.get(i);
			String bomName = getBomName(obj);
			String tReplaceName = getBomName(tBomObj);
			if(bomName.toUpperCase().equals(tReplaceName.toUpperCase()))
			{
				mBomList.set(i, tBomObj)  ;
			}
			
		}
		return true;
	}
	
	public boolean addBomItemValue(String tBomName,String tBomItemName,int arrayPos,String tValue)
	{
		for(int i=0;i<this.mBomList.size();i++)
		{
			Object obj = mBomList.get(i);
			String bomName = getBomName(obj);
			if(bomName.toUpperCase().equals(tBomName.toUpperCase()))
			{
				if(obj.getClass().isArray())
				{
					//如果是数组.
					if(Array.get(obj, arrayPos)==null)
					{
						return false;
					}
					this.setBomField((AbstractBOM)Array.get(obj, arrayPos), tBomName, tBomItemName, tValue);
					
					break;
				}
				else
				{
					//非数组,直接设置
					this.setBomField((AbstractBOM)obj, tBomName, tBomItemName, tValue);
					break;
				}
			}
			
		}
		return true;
	}
	
	
	protected String getBomClassName(Object objBom){
		String className ;
		if(objBom.getClass().isArray())
			className = objBom.getClass().getComponentType().getName();
		else
			className = objBom.getClass().getName();
		return className;

	}
	
	protected String getBomName(Object objBom){
		String className = getBomClassName(objBom);
		String bomName;
		int index = className.lastIndexOf(".");
		if(index>0)
			className = className.substring(index+1);
		bomName = className;
	//	if(className.startsWith("BOM"))
		//	bomName = className.substring(3);
	    return bomName;		
	}
	
	public ArrayList getBomList()
	{
		return this.mBomList;
	}
	
	private boolean setBomField(AbstractBOM objBom,String bomName,String fieldName,String tFieldValue){
		Object obj = null;
		try {
			String type = BOMInfo.getInstance().getBomItemType(bomName, fieldName, objBom);
			
			Object[] para = new Object[1];
			Class[] paras = new Class[1];
			if(type==BOMInfo.STRING){
				para[0] = tFieldValue;
				type = "String";
				paras[0]=String.class;
			}else if(type==BOMInfo.DATE){
				try {
					para[0] = sdf.parse(tFieldValue);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					try {
						para[0] = sdf.parse(tFieldValue+" 00:00:00");
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				type = "Date";
				paras[0]=Date.class;
			}else if(type==BOMInfo.NUMBER){
				para[0]  = Double.parseDouble(tFieldValue);
				paras[0]=Double.class;
				type = "Double";
				
				
			}
			
			//para[0] = type;
			Method method = objBom.getClass().getMethod("set"+fieldName,paras);
			if(method!=null)
				obj = method.invoke(objBom, para);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	    
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BOMCont cont = new BOMCont();
		cont.setAccName("12222");
		
		
		BuildBomList t = new BuildBomList();
		//t.setBomField(cont, "BOMCont", "FirstTrialDate", "2011-01-01 01:00:00");
		//logger.debug("$$$:"+cont.getFirstTrialDate());
		TransferData tt = new TransferData();
		tt.setNameAndValue("insuredCount", 2);
		t.initAllParams(tt);
		//t.addBomItemValue("BOMInsured", "OUWFlag4", 1, "y");
		t.replaceBom(cont);
	}

}
