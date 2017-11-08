package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPContStateDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpContStateDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccTraceDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsureAccTraceSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsureAccTraceSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsureAccTraceSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsureAccTraceSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 团体保全集体保全确认功能类</p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: Sinosoft</p>
 * @author  sunsx 2008-09-27
 * @version 1.0
 */
public class GrpEdorConfirmBL {
private static Logger logger = Logger.getLogger(GrpEdorConfirmBL.class);

	private Reflections mRef = new Reflections();


	/**
	 * 团体保全确认集体保单函数
	 * @param tLPGrpEdorItemSchema
	 * @return
	 */
	public boolean confirmGrpCont(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tGrpContNo = tLPGrpEdorItemSchema.getGrpContNo();//集体保单号
		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//查询P表
		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		tLPGrpContDB.setGrpContNo(tGrpContNo);
		tLPGrpContDB.setEdorNo(tEdorNo);
		tLPGrpContDB.setEdorType(tEdorType);
		if (tLPGrpContDB.getInfo())
		{
			//查询C表
			LCGrpContDB tLCGrpContDB = new LCGrpContDB();
			tLCGrpContDB.setGrpContNo(tGrpContNo);
			if (!tLCGrpContDB.getInfo())
			{
				errors.copyAllErrors(tLCGrpContDB.mErrors);

				return false;
			}

			LCGrpContSchema newLCGrpContSchema = new LCGrpContSchema();
			LPGrpContSchema newLPGrpContSchema = new LPGrpContSchema();

			try {
				//PC互换
				mRef.transFields(newLCGrpContSchema, tLPGrpContDB.getSchema());
				mRef.transFields(newLPGrpContSchema, tLCGrpContDB.getSchema());
			} catch (Exception e) {

				errors.addOneError("团体保全确认公共函数团体保单PC互换出错!");
				return false;
			}

			newLCGrpContSchema.setModifyDate(PubFun.getCurrentDate());
			newLCGrpContSchema.setModifyTime(PubFun.getCurrentTime());

			map.put(newLCGrpContSchema, "DELETE&INSERT");

			newLPGrpContSchema.setEdorNo(tEdorNo);
			newLPGrpContSchema.setEdorType(tEdorType);
			newLPGrpContSchema.setModifyDate(PubFun.getCurrentDate());
			newLPGrpContSchema.setModifyTime(PubFun.getCurrentTime());

			map.put(newLPGrpContSchema, "DELETE&INSERT");
		}

		return true;
	}
	
	/**
	 * 团体保全确认集体保单帐户履历函数
	 * @param tLPGrpEdorItemSchema
	 * @return
	 */
	public boolean confirmGrpTraceSet(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//查询P表
		LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
		//旧的P表数据集合
		LPInsureAccTraceSet tLPInsureAccTraceSetOld = null;
		tLPInsureAccTraceDB.setEdorNo(tEdorNo);
		tLPInsureAccTraceDB.setEdorType(tEdorType);
		try{
			tLPInsureAccTraceSetOld = tLPInsureAccTraceDB.query();
		} catch (Exception ex){
			CError.buildErr(this, ex.toString());
			//ex.printStackTrace();
			return false;
		}
		if (tLPInsureAccTraceDB.mErrors.needDealError()){
			errors.copyAllErrors(tLPInsureAccTraceDB.mErrors);
			return false;
		}
		//如果存在P表数据,则进行PC互换
		if(tLPInsureAccTraceSetOld.size() > 0){

			//新的C表数据
			LCInsureAccTraceSet tLCInsureAccTraceSetNew = new LCInsureAccTraceSet();
			LCInsureAccTraceSchema tLCInsureAccTraceSchemaNew = null;

			LPInsureAccTraceSchema tLPInsureAccTraceSchemaOld = null;

			//开始PC互换
			for(int i = 1; i<= tLPInsureAccTraceSetOld.size();i++){

				tLCInsureAccTraceSchemaNew = new LCInsureAccTraceSchema();
				tLPInsureAccTraceSchemaOld = tLPInsureAccTraceSetOld.get(i);
				mRef.transFields(tLCInsureAccTraceSchemaNew,tLPInsureAccTraceSchemaOld);
				tLCInsureAccTraceSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccTraceSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCInsureAccTraceSetNew.add(tLCInsureAccTraceSchemaNew);

			}
			map.put(tLCInsureAccTraceSetNew, "DELETE&INSERT");
		}

		return true;
	}

	/**
	 * 团体保全确认集体保单GrpPol函数
	 * @param tLPGrpEdorItemSchema
	 * @return
	 */
	public boolean confirmGrpPolSet(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tGrpContNo = tLPGrpEdorItemSchema.getGrpContNo();//集体保单号
		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//旧的P表数据集合
		LPGrpPolSet tLPGrpPolSetOld = null;

		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		tLPGrpPolDB.setEdorNo(tEdorNo);
		tLPGrpPolDB.setEdorType(tEdorType);
		tLPGrpPolDB.setGrpContNo(tGrpContNo);

		try {
			tLPGrpPolSetOld = tLPGrpPolDB.query();
		} catch (Exception e) {
			errors.addOneError("团体保全确认公共函数团体保单信息查询出错!");
			return false;
		}

		//如果存在P表数据则进行互换
		if(tLPGrpPolSetOld.size() > 0){
			//新的C表数据
			LCGrpPolSet tLCGrpPolSetNew = new LCGrpPolSet();
			LCGrpPolSchema tLCGrpPolSchemaNew = null;
			//新的P表数据
			LPGrpPolSet tLPGrpPolSetNew = new LPGrpPolSet();
			LPGrpPolSchema tLPGrpPolSchemaNew = null;
			
			//旧的Schema数据
			LCGrpPolSchema tLCGrpPolSchemaOld = null;
			LPGrpPolSchema tLPGrpPolSchemaOld = null;
			
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			
			//开始PC互换
			for(int i = 1; i <= tLPGrpPolSetOld.size(); i++){
				
				tLCGrpPolSchemaNew = new LCGrpPolSchema();
				tLPGrpPolSchemaOld = tLPGrpPolSetOld.get(i);
				mRef.transFields(tLCGrpPolSchemaNew, tLPGrpPolSchemaOld);
				tLCGrpPolSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCGrpPolSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCGrpPolSetNew.add(tLCGrpPolSchemaNew);
				
				tLCGrpPolDB.setGrpPolNo(tLCGrpPolSchemaNew.getGrpPolNo());
				if(!tLCGrpPolDB.getInfo()){
					
					errors.copyAllErrors(tLCGrpPolDB.mErrors);
					return false;
					
				}
				
				tLCGrpPolSchemaOld = tLCGrpPolDB.getSchema();
				tLPGrpPolSchemaNew = new LPGrpPolSchema();
				mRef.transFields(tLPGrpPolSchemaNew, tLCGrpPolSchemaOld);
				tLPGrpPolSchemaNew.setEdorNo(tEdorNo);
				tLPGrpPolSchemaNew.setEdorType(tEdorType);
				tLPGrpPolSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLPGrpPolSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLPGrpPolSetNew.add(tLPGrpPolSchemaNew);
				
				
			}
			map.put(tLPGrpPolSetNew, "DELETE&INSERT");
			map.put(tLCGrpPolSetNew, "DELETE&INSERT");
		}

		return true;
	}
	
	public boolean confirmGrpAccSet(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//旧的P表数据集合
		LPInsureAccSet tLPInsureAccSetOld = null;

		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		tLPInsureAccDB.setEdorNo(tEdorNo);
		tLPInsureAccDB.setEdorType(tEdorType);

		try {
			tLPInsureAccSetOld = tLPInsureAccDB.query();
		} catch (Exception e) {
			errors.addOneError("团体保全确认公共函数团体保单信息查询出错!");
			return false;
		}

		//如果存在P表数据则进行互换
		if(tLPInsureAccSetOld.size() > 0){
			//新的C表数据
			LCInsureAccSet tLCInsureAccSetNew = new LCInsureAccSet();
			LCInsureAccSchema tLCInsureAccSchemaNew = null;
			//新的P表数据
			LPInsureAccSet tLPInsureAccSetNew = new LPInsureAccSet();
			LPInsureAccSchema tLPInsureAccSchemaNew = null;
			
			//旧的Schema数据
			LCInsureAccSchema tLCInsureAccSchemaOld = null;
			LPInsureAccSchema tLPInsureAccSchemaOld = null;
			
			LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
			
			//开始PC互换
			for(int i = 1; i <= tLPInsureAccSetOld.size(); i++){
				
				tLCInsureAccSchemaNew = new LCInsureAccSchema();
				tLPInsureAccSchemaOld = tLPInsureAccSetOld.get(i);
				mRef.transFields(tLCInsureAccSchemaNew, tLPInsureAccSchemaOld);
				tLCInsureAccSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCInsureAccSetNew.add(tLCInsureAccSchemaNew);
				
				tLCInsureAccDB.setPolNo(tLCInsureAccSchemaNew.getPolNo());
				tLCInsureAccDB.setInsuAccNo(tLCInsureAccSchemaNew.getInsuAccNo());
				if(!tLCInsureAccDB.getInfo()){
					
					errors.copyAllErrors(tLCInsureAccDB.mErrors);
					return false;
					
				}
				
				tLCInsureAccSchemaOld = tLCInsureAccDB.getSchema();
				tLPInsureAccSchemaNew = new LPInsureAccSchema();
				mRef.transFields(tLPInsureAccSchemaNew, tLCInsureAccSchemaOld);
				tLPInsureAccSchemaNew.setEdorNo(tEdorNo);
				tLPInsureAccSchemaNew.setEdorType(tEdorType);
				tLPInsureAccSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLPInsureAccSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLPInsureAccSetNew.add(tLPInsureAccSchemaNew);
				
				
			}
			map.put(tLPInsureAccSetNew, "DELETE&INSERT");
			map.put(tLCInsureAccSetNew, "DELETE&INSERT");
		}

		return true;
	}

	
	public boolean confirmGrpAccClassSet(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//旧的P表数据集合
		LPInsureAccClassSet tLPInsureAccClassSetOld = null;

		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		tLPInsureAccClassDB.setEdorNo(tEdorNo);
		tLPInsureAccClassDB.setEdorType(tEdorType);

		try {
			tLPInsureAccClassSetOld = tLPInsureAccClassDB.query();
		} catch (Exception e) {
			errors.addOneError("团体保全确认公共函数团体保单信息查询出错!");
			return false;
		}

		//如果存在P表数据则进行互换
		if(tLPInsureAccClassSetOld.size() > 0){
			//新的C表数据
			LCInsureAccClassSet tLCInsureAccClassSetNew = new LCInsureAccClassSet();
			LCInsureAccClassSchema tLCInsureAccClassSchemaNew = null;
			//新的P表数据
			LPInsureAccClassSet tLPInsureAccClassSetNew = new LPInsureAccClassSet();
			LPInsureAccClassSchema tLPInsureAccClassSchemaNew = null;
			
			//旧的Schema数据
			LCInsureAccClassSchema tLCInsureAccClassSchemaOld = null;
			LPInsureAccClassSchema tLPInsureAccClassSchemaOld = null;
			
			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			
			//开始PC互换
			for(int i = 1; i <= tLPInsureAccClassSetOld.size(); i++){
				
				tLCInsureAccClassSchemaNew = new LCInsureAccClassSchema();
				tLPInsureAccClassSchemaOld = tLPInsureAccClassSetOld.get(i);
				mRef.transFields(tLCInsureAccClassSchemaNew, tLPInsureAccClassSchemaOld);
				tLCInsureAccClassSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCInsureAccClassSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCInsureAccClassSetNew.add(tLCInsureAccClassSchemaNew);
				
				tLCInsureAccClassDB.setPolNo(tLCInsureAccClassSchemaNew.getPolNo());
				tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccClassSchemaNew.getInsuAccNo());
				tLCInsureAccClassDB.setPayPlanCode(tLCInsureAccClassSchemaNew.getPayPlanCode());
				tLCInsureAccClassDB.setOtherNo(tLCInsureAccClassSchemaNew.getOtherNo());
				tLCInsureAccClassDB.setAccAscription(tLCInsureAccClassSchemaNew.getAccAscription());
				if(!tLCInsureAccClassDB.getInfo()){
					
					errors.copyAllErrors(tLCInsureAccClassDB.mErrors);
					return false;
					
				}
				
				tLCInsureAccClassSchemaOld = tLCInsureAccClassDB.getSchema();
				tLPInsureAccClassSchemaNew = new LPInsureAccClassSchema();
				mRef.transFields(tLPInsureAccClassSchemaNew, tLCInsureAccClassSchemaOld);
				tLPInsureAccClassSchemaNew.setEdorNo(tEdorNo);
				tLPInsureAccClassSchemaNew.setEdorType(tEdorType);
				tLPInsureAccClassSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLPInsureAccClassSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLPInsureAccClassSetNew.add(tLPInsureAccClassSchemaNew);
				
				
			}
			map.put(tLPInsureAccClassSetNew, "DELETE&INSERT");
			map.put(tLCInsureAccClassSetNew, "DELETE&INSERT");
		}

		return true;
	}
	
	/**
	 * 团体保全确认个人保单Pol函数
	 * @param tLPGrpEdorItemSchema
	 * @return
	 */
	public boolean confirmPolSet(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tGrpContNo = tLPGrpEdorItemSchema.getGrpContNo();//集体保单号
		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//旧的P表数据集合
		LPPolSet tLPPolSetOld = null;

		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(tEdorNo);
		tLPPolDB.setEdorType(tEdorType);
		tLPPolDB.setGrpContNo(tGrpContNo);

		try {
			tLPPolSetOld = tLPPolDB.query();
		} catch (Exception e) {
			errors.addOneError("团体保全确认公共函数团体保单信息查询出错!");
			return false;
		}

		//如果存在P表数据则进行互换
		if(tLPPolSetOld.size() > 0){
			//新的C表数据
			LCPolSet tLCPolSetNew = new LCPolSet();
			LCPolSchema tLCPolSchemaNew = null;
			//新的P表数据
			LPPolSet tLPPolSetNew = new LPPolSet();
			LPPolSchema tLPPolSchemaNew = null;
			
			//旧的Schema数据
			LCPolSchema tLCPolSchemaOld = null;
			LPPolSchema tLPPolSchemaOld = null;
			
			LCPolDB tLCPolDB = new LCPolDB();
			
			//开始PC互换
			for(int i = 1; i <= tLPPolSetOld.size(); i++){
				
				tLCPolSchemaNew = new LCPolSchema();
				tLPPolSchemaOld = tLPPolSetOld.get(i);
				mRef.transFields(tLCPolSchemaNew, tLPPolSchemaOld);
				tLCPolSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCPolSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCPolSetNew.add(tLCPolSchemaNew);
				
				tLCPolDB.setPolNo(tLCPolSchemaNew.getPolNo());
				if(tLCPolDB.getInfo()){
					//如果存在C表数据则将C表数据导入到P表
					tLCPolSchemaOld = tLCPolDB.getSchema();
					tLPPolSchemaNew = new LPPolSchema();
					mRef.transFields(tLPPolSchemaNew, tLCPolSchemaOld);
					tLPPolSchemaNew.setEdorNo(tEdorNo);
					tLPPolSchemaNew.setEdorType(tEdorType);
					tLPPolSchemaNew.setModifyDate(PubFun.getCurrentDate());
					tLPPolSchemaNew.setModifyTime(PubFun.getCurrentTime());
					tLPPolSetNew.add(tLPPolSchemaNew);
				}
				
				
			}
			map.put(tLPPolSetNew, "DELETE&INSERT");
			map.put(tLCPolSetNew, "DELETE&INSERT");
		}

		return true;
	}
	
	/**
	 * 团体保全确认个人保单Pol函数
	 * @param tLPGrpEdorItemSchema
	 * @return
	 */
	public boolean confirmContSet(LPGrpEdorItemSchema tLPGrpEdorItemSchema,MMap map,CErrors errors){


		String tGrpContNo = tLPGrpEdorItemSchema.getGrpContNo();//集体保单号
		String tEdorNo = tLPGrpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = tLPGrpEdorItemSchema.getEdorType();//保全类型

		//旧的P表数据集合
		LPContSet tLPContSetOld = null;

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setEdorNo(tEdorNo);
		tLPContDB.setEdorType(tEdorType);
		tLPContDB.setGrpContNo(tGrpContNo);

		try {
			tLPContSetOld = tLPContDB.query();
		} catch (Exception e) {
			errors.addOneError("团体保全确认公共函数团体保单信息查询出错!");
			return false;
		}

		//如果存在P表数据则进行互换
		if(tLPContSetOld.size() > 0){
			//新的C表数据
			LCContSet tLCContSetNew = new LCContSet();
			LCContSchema tLCContSchemaNew = null;
			//新的P表数据
			LPContSet tLPContSetNew = new LPContSet();
			LPContSchema tLPContSchemaNew = null;
			
			//旧的Schema数据
			LCContSchema tLCContSchemaOld = null;
			LPContSchema tLPContSchemaOld = null;
			
			LCContDB tLCContDB = new LCContDB();
			
			//开始PC互换
			for(int i = 1; i <= tLPContSetOld.size(); i++){
				
				tLCContSchemaNew = new LCContSchema();
				tLPContSchemaOld = tLPContSetOld.get(i);
				mRef.transFields(tLCContSchemaNew, tLPContSchemaOld);
				tLCContSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCContSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCContSetNew.add(tLCContSchemaNew);
				
				tLCContDB.setContNo(tLCContSchemaNew.getContNo());
				if(tLCContDB.getInfo()){
					//如果存在C表数据则将C表数据导入到P表
					tLCContSchemaOld = tLCContDB.getSchema();
					tLPContSchemaNew = new LPContSchema();
					mRef.transFields(tLPContSchemaNew, tLCContSchemaOld);
					tLPContSchemaNew.setEdorNo(tEdorNo);
					tLPContSchemaNew.setEdorType(tEdorType);
					tLPContSchemaNew.setModifyDate(PubFun.getCurrentDate());
					tLPContSchemaNew.setModifyTime(PubFun.getCurrentTime());
					tLPContSetNew.add(tLPContSchemaNew);
				}
				
				
			}
			map.put(tLPContSetNew, "DELETE&INSERT");
			map.put(tLCContSetNew, "DELETE&INSERT");
		}

		return true;
	}
	public boolean confirmGrpContState(LPGrpEdorItemSchema grpEdorItemSchema, MMap map, CErrors errors) {
		String tEdorNo = grpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = grpEdorItemSchema.getEdorType();//保全类型

		//查询P表
		LPGrpContStateDB tLPGrpContStateDB = new LPGrpContStateDB();
		//旧的P表数据集合
		LPGrpContStateSet tLPGrpContStateSetOld = null;
		tLPGrpContStateDB.setEdorNo(tEdorNo);
		tLPGrpContStateDB.setEdorType(tEdorType);
		try{
			tLPGrpContStateSetOld = tLPGrpContStateDB.query();
		} catch (Exception ex){
			CError.buildErr(this, ex.toString());
			//ex.printStackTrace();
			return false;
		}
		if (tLPGrpContStateDB.mErrors.needDealError()){
			errors.copyAllErrors(tLPGrpContStateDB.mErrors);
			return false;
		}
		//如果存在P表数据,则进行PC互换
		if(tLPGrpContStateSetOld.size() > 0){

			//新的C表数据
			LCGrpContStateSet tLCGrpContStateSetNew = new LCGrpContStateSet();
			LCGrpContStateSchema tLCGrpContStateSchemaNew = null;

			LPGrpContStateSchema tLPGrpContStateSchemaOld = null;

			//开始PC互换
			for(int i = 1; i<= tLPGrpContStateSetOld.size();i++){

				tLCGrpContStateSchemaNew = new LCGrpContStateSchema();
				tLPGrpContStateSchemaOld = tLPGrpContStateSetOld.get(i);
				mRef.transFields(tLCGrpContStateSchemaNew,tLPGrpContStateSchemaOld);
				tLCGrpContStateSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCGrpContStateSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCGrpContStateSetNew.add(tLCGrpContStateSchemaNew);

			}
			map.put(tLCGrpContStateSetNew, "DELETE&INSERT");
		}

		return true;
	}
	
	public boolean confirmContState(LPGrpEdorItemSchema grpEdorItemSchema, MMap map, CErrors errors) {
		String tEdorNo = grpEdorItemSchema.getEdorNo();//保全批单号
		String tEdorType = grpEdorItemSchema.getEdorType();//保全类型

		//查询P表
		LPContStateDB tLPContStateDB = new LPContStateDB();
		//旧的P表数据集合
		LPContStateSet tLPContStateSetOld = null;
		tLPContStateDB.setEdorNo(tEdorNo);
		tLPContStateDB.setEdorType(tEdorType);
		try{
			tLPContStateSetOld = tLPContStateDB.query();
		} catch (Exception ex){
			CError.buildErr(this, ex.toString());
			//ex.printStackTrace();
			return false;
		}
		if (tLPContStateDB.mErrors.needDealError()){
			errors.copyAllErrors(tLPContStateDB.mErrors);
			return false;
		}
		//如果存在P表数据,则进行PC互换
		if(tLPContStateSetOld.size() > 0){

			//新的C表数据
			LCContStateSet tLCContStateSetNew = new LCContStateSet();
			LCContStateSchema tLCContStateSchemaNew = null;

			LPContStateSchema tLPContStateSchemaOld = null;

			//开始PC互换
			for(int i = 1; i<= tLPContStateSetOld.size();i++){

				tLCContStateSchemaNew = new LCContStateSchema();
				tLPContStateSchemaOld = tLPContStateSetOld.get(i);
				mRef.transFields(tLCContStateSchemaNew,tLPContStateSchemaOld);
				tLCContStateSchemaNew.setModifyDate(PubFun.getCurrentDate());
				tLCContStateSchemaNew.setModifyTime(PubFun.getCurrentTime());
				tLCContStateSetNew.add(tLCContStateSchemaNew);

			}
			map.put(tLCContStateSetNew, "DELETE&INSERT");
		}

		return true;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
	}


}
