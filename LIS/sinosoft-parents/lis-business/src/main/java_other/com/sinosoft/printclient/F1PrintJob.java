package com.sinosoft.printclient;
import org.apache.log4j.Logger;

import java.awt.HeadlessException;
import java.awt.print.*;

import javax.print.PrintService;
import javax.print.attribute.PrintRequestAttributeSet;

public class F1PrintJob 
{
private static Logger logger = Logger.getLogger(F1PrintJob.class);

	private PrinterJob mPrinterJob = null;

	private String mTempFile;

	private String[] mBarCode;

	private String[] mBarPara;

	private String[] mLocations;

	private String[] mBarType;

	public F1PrintJob(PrinterJob aPrinterJob)
	{
		mPrinterJob = aPrinterJob;
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

	public void setPrintable(Printable aPrintable)
	{
		logger.debug("setPrintable");

		F1Printable mF1Printable = new F1Printable(aPrintable);
		mF1Printable.setBarCode(mBarPara, mLocations, mBarCode,mBarType);
		mPrinterJob.setPrintable(mF1Printable);
	}

	public void setPrintable(Printable painter, PageFormat format)
	{
		F1Printable mF1Printable = new F1Printable(painter);
		mF1Printable.setBarCode(mBarPara, mLocations, mBarCode,mBarType);
		mPrinterJob.setPrintable(mF1Printable, format);
	}

	public void setPrintService(PrintService service) throws PrinterException
	{
		mPrinterJob.setPrintService(service);
	}

	public void setPageable(Pageable document) throws NullPointerException
	{
		F1Pageable tF1Pageable = new F1Pageable(document);
		tF1Pageable.setBarCode(mBarPara, mLocations, mBarCode,mBarType);
		mPrinterJob.setPageable(tF1Pageable);
	}

	public boolean printDialog() throws HeadlessException
	{
		return mPrinterJob.printDialog();
	}

	public boolean printDialog(PrintRequestAttributeSet attributes) throws HeadlessException
	{

		if (attributes == null)
		{
			throw new NullPointerException("attributes");
		}
		return mPrinterJob.printDialog();
	}

	public PageFormat pageDialog(PageFormat page) throws HeadlessException
	{
		return mPrinterJob.pageDialog(page);
	}

	public PageFormat pageDialog(PrintRequestAttributeSet attributes) throws HeadlessException
	{

		if (attributes == null)
		{
			throw new NullPointerException("attributes");
		}
		return mPrinterJob.pageDialog(defaultPage());
	}

	public PageFormat defaultPage(PageFormat page)
	{
		return mPrinterJob.defaultPage(page);
	}

	public PageFormat defaultPage()
	{
		return mPrinterJob.defaultPage(new PageFormat());
	}

	public PageFormat validatePage(PageFormat page)
	{
		return mPrinterJob.validatePage(page);
	}

	public void print() throws PrinterException
	{
		mPrinterJob.print();
	}

	public void print(PrintRequestAttributeSet attributes) throws PrinterException
	{
		mPrinterJob.print(attributes);
	}

	public void setCopies(int copies)
	{
		mPrinterJob.setCopies(copies);
	}

	public int getCopies()
	{
		return mPrinterJob.getCopies();
	}

	public String getUserName()
	{
		return mPrinterJob.getUserName();
	}

	public void setJobName(String jobName)
	{
		mPrinterJob.setJobName(jobName);
	}

	public String getJobName()
	{
		return mPrinterJob.getJobName();
	}

	public void cancel()
	{
		mPrinterJob.cancel();
	}

	public boolean isCancelled()
	{
		return mPrinterJob.isCancelled();
	}

}
