package com.sinosoft.printclient;
import org.apache.log4j.Logger;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

public class F1Pageable implements Pageable
{
private static Logger logger = Logger.getLogger(F1Pageable.class);


	private Pageable mPageable = null;

	private String[] mBarCode;

	private String[] mBarPara;

	private String[] mLocations;

	private String[] mBarType;

	public F1Pageable(Pageable pageable)
	{
		this.mPageable = pageable;
	}

	public void setBarCode(String[] aBarPara, String[] alocations, String[] aBarCode, String[] aBarType)
	{
		mLocations = alocations;
		mBarPara = aBarPara;
		mBarCode = aBarCode;
		mBarType = aBarType;
	}

//	public void setBarCode(String[] aBarPara, String[] alocations, String[] aBarCode)
//	{
//		mLocations = alocations;
//		mBarPara = aBarPara;
//		mBarCode = aBarCode;
//		mBarType = null;
//	}

	public int getNumberOfPages()
	{

		return this.mPageable.getNumberOfPages();
	}

	public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException
	{

		return this.mPageable.getPageFormat(pageIndex);
	}

	public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException
	{
		Printable printable = this.mPageable.getPrintable(pageIndex);
		F1Printable myprintable = new F1Printable(printable);
		myprintable.setBarCode(mBarPara, mLocations, mBarCode,mBarType);
		return myprintable;
	}

}
