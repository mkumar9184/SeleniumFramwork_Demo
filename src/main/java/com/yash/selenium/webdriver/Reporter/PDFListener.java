package com.yash.selenium.webdriver.Reporter;
import com.yash.selenium.webdriver.*;
import com.yash.selenium.webdriver.Configuration.SingleBrowser;

import org.testng.IExecutionListener;
import org.testng.IInvokedMethodListener;
import org.testng.ISuiteListener;
import org.testng.ITestListener;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.IExecutionListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class PDFListener implements ITestListener, ISuiteListener, IInvokedMethodListener, IExecutionListener{

	WebDriver driver = null;
                /**
                * Document
                */
                private Document document = null;

                public String reportName;

                /**
                * PdfPTables
                */
                PdfPTable statTable = null;
                PdfPTable chartTable = null;
                PdfPTable successTable = null; 
                PdfPTable failTable = null; 
                PdfPTable skipTable = null;
                PdfPTable headerofTable=null;
                PdfPTable successStep = null;
                PdfPTable failedStep=null;
                PdfPTable detailsSummary=null;




                /**
                * throwableMap
                */
                private HashMap<Integer, Throwable> throwableMap = null;

                /**
                * nbExceptions
                */
                private int nbExceptions = 0;

                /**
                * nbTotalTime
                */
                private long nbTotalTime = 0;


                FileOutputStream fop = null;
                File file;


                /**
                * PDFListener
                */          
                public PDFListener() {
                                log("PDFListener()");
                                this.document = new Document();
                                this.throwableMap = new HashMap<Integer, Throwable>();
                                PageFactory.initElements(driver, this);
                }


                /**
                * getResults(ITestResult result)
                */
                public ITestResult getResults(ITestResult result){
                                return result;
                }



                /**
                * @see org.testng.ITestListener#onTestSuccess(com.beust.testng.ITestResult)
                */
                public void onTestSuccess(ITestResult result) {
                                log("onTestSuccess("+result+")");

                                if (successTable == null) {
                                                this.successTable = new PdfPTable(new float[]{.4f, .4f, .3f, .3f});

                                }


                                PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().getRealClass().getSimpleName(),new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
                                this.successTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(),new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
                                this.successTable.addCell(cell);

                                long duration = result.getEndMillis()-result.getStartMillis();
                                nbTotalTime += duration;
                                cell = new PdfPCell(new Paragraph("" + duration));

                                this.successTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("PASSED",new Font(FontFamily.TIMES_ROMAN, 10,Font.BOLD,BaseColor.GREEN)));
                                this.successTable.addCell(cell);

                                if (this.successStep == null) {
                                                this.successStep = new PdfPTable(new float[]{1});
                                                this.successStep.setTotalWidth(30);
                                }


                                Paragraph p = new Paragraph(result.getTestClass().getRealClass().getSimpleName() +"."+result.getMethod().getMethodName(), new Font(FontFamily.TIMES_ROMAN, 11, Font.BOLD));
                                cell = new PdfPCell(p);
                                successStep.addCell(cell);
                                p = new Paragraph("Test Steps : " , new Font(FontFamily.TIMES_ROMAN, 11, Font.NORMAL));
                                cell = new PdfPCell(p);
                                cell.setPaddingLeft(5f);
                                cell.setPaddingBottom(5f);
                                cell.setBorderWidthBottom(0f);
                                this.successStep.addCell(cell);
                                for(String item:Reporter.getOutput(result)){
                                                p = new Paragraph(item , new Font(FontFamily.TIMES_ROMAN, 9, Font.NORMAL));
                                                p.setAlignment(Element.ALIGN_CENTER);
                                                cell = new PdfPCell(p);
                                                cell.setBorderWidthBottom(0f);
                                                cell.setBorderWidthTop(0f);
                                                cell.setPaddingLeft(10f);
                                                this.successStep.addCell(cell);
                                }
                                p = new Paragraph();
                                cell = new PdfPCell(p);
                                this.successStep.addCell(cell);
                }

                /**
                * @see com.beust.testng.ITestListener#onTestFailure(com.beust.testng.ITestResult)
                */
                public void onTestFailure(ITestResult result) {
                                log("onTestFailure("+result+")");

                                if (this.failTable == null) {
                                                this.failTable = new PdfPTable(new float[]{.4f, .4f, .3f, .3f});
                                }

                                PdfPCell cell  = new PdfPCell(new Paragraph(result.getTestClass().getRealClass().getSimpleName(),new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
                                this.failTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph(result.getMethod().getMethodName(),new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL)));
                                this.failTable.addCell(cell);
                                long duration = result.getEndMillis()-result.getStartMillis();
                                nbTotalTime += duration;
                                cell = new PdfPCell(new Paragraph("" + duration));
                                this.failTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("FAILED",new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD,BaseColor.RED)));
                                cell.setBorderColorBottom(BaseColor.BLUE);
                                this.failTable.addCell(cell);   



                                if (this.failedStep == null) {
                                                this.failedStep = new PdfPTable(1);
                                }

                                Paragraph p = new Paragraph(result.getTestClass().getRealClass().getSimpleName() +"."+result.getMethod().getMethodName(), new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
                                p.setAlignment(Element.ALIGN_JUSTIFIED);
                                cell = new PdfPCell(p);
                                this.failedStep.addCell(cell);

                                Image image=takeScreenShot(result.getMethod().getMethodName());

                                cell = new PdfPCell();
                                cell.setImage(image);
                                //cell.addElement(image);
                                this.failedStep.addCell(cell);

                                p = new Paragraph("Exceptions : ", new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD,BaseColor.RED));
                                p.setAlignment(Element.ALIGN_CENTER);
                                cell = new PdfPCell(p);
                                cell.setPaddingLeft(5f);
                                cell.setBorderWidthBottom(0f);
                                //cell.setBorder(0);
                                this.failedStep.addCell(cell);
                                Throwable throwable = result.getThrowable();
                                if (throwable != null) {
                                                this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
                                                this.nbExceptions++;
                                                Paragraph excep = new Paragraph(new Chunk(Utils.stackTrace(throwable, true)[0],new Font(FontFamily.TIMES_ROMAN, 9, Font.NORMAL)).setLocalGoto("" + throwable.hashCode()));
                                                excep.setAlignment(Element.ALIGN_LEFT);
                                                cell = new PdfPCell(excep);
                                                cell.setPaddingLeft(10f);
                                                cell.setBorderWidthBottom(0f);
                                                cell.setBorderWidthTop(0f);
                                                this.failedStep.addCell(cell);
                                }

                                p = new Paragraph("Test Steps : ", new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL));
                                p.setAlignment(Element.ALIGN_CENTER);
                                cell = new PdfPCell(p);
                                cell.setPaddingLeft(5f);
                                cell.setBorderWidthBottom(0f);
                                cell.setBorderWidthTop(0f);
                                this.failedStep.addCell(cell);
                                /*p = new Paragraph("" + Reporter.getOutput(), new Font(FontFamily.TIMES_ROMAN, 7, Font.NORMAL));
                                p.setAlignment(Element.ALIGN_LEFT);
                                List unorderedList = new List(List.UNORDERED);
                                */                          
                                for(String item:Reporter.getOutput(result)){
                                                p = new Paragraph(item , new Font(FontFamily.TIMES_ROMAN, 9, Font.NORMAL));
                                                p.setAlignment(Element.ALIGN_CENTER);
                                                cell = new PdfPCell(p);
                                                cell.setBorderWidthBottom(0f);
                                                cell.setBorderWidthTop(0f);
                                                cell.setPaddingLeft(10f);
                                                this.failedStep.addCell(cell);
                                }
                                /*cell = new PdfPCell(p);
                                cell.setBorderWidthTop(0f);
                                cell.setPaddingLeft(5f);
                                cell.addElement(unorderedList);
                                this.failedStep.addCell(cell);*/
                }

                @SuppressWarnings("deprecation")
                public Image takeScreenShot(String methodName) {
                                Image image=null;
                                try {
 //                                               File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                                				 File scrFile = ((TakesScreenshot) SingleBrowser.getInstance()).getScreenshotAs(OutputType.FILE);
                                                image=Image.getInstance(scrFile.toURL());
                                } catch (Exception e) {
                                                e.printStackTrace();
                                } 
                                return image;
                }
                public void setHeader() {

                                if (this.headerofTable == null) {
                                                this.headerofTable = new PdfPTable(new float[]{.4f, .4f, .3f, .3f});
                                                this.headerofTable.setTotalWidth(25f);
                                }else{
                                                return ;
                                }


                                PdfPCell cell = new PdfPCell();
                                cell.setColspan(4);
                                cell = new PdfPCell(new Paragraph("Class"));
                                cell.setBackgroundColor(BaseColor.GRAY);
                                this.headerofTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Method"));
                                cell.setBackgroundColor(BaseColor.GRAY);
                                this.headerofTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Time (ms)"));
                                cell.setBackgroundColor(BaseColor.GRAY);
                                this.headerofTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Status"));
                                cell.setBackgroundColor(BaseColor.GRAY);
                                this.headerofTable.addCell(cell);

                }

                public void setDetailheader(){
                                if (this.detailsSummary == null) {
                                                this.detailsSummary = new PdfPTable(new float[]{30});
                                                this.detailsSummary.setTotalWidth(30f);
                                }else{
                                                return ;
                                }

                                Paragraph p = new Paragraph(" OrangeHr Test Automation Details Result ",FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
                                p.setAlignment(Element.ALIGN_CENTER);

                                PdfPCell cell = new PdfPCell(p);
                                cell.setBackgroundColor(BaseColor.GRAY);
                                this.detailsSummary.addCell(cell);

                }
                /**
                * @see com.beust.testng.ITestListener#onTestSkipped(com.beust.testng.ITestResult)
                */
                public void onTestSkipped(ITestResult result) {
                                log("onTestSkipped("+result+")");
                                if (this.skipTable == null) {
                                                this.skipTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f});
                                                this.skipTable.setTotalWidth(20f);
                                }

                                Paragraph p = new Paragraph("SKIPPED TESTS  ", new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
                                p.setAlignment(Element.ALIGN_CENTER);
                                PdfPCell cell = new PdfPCell(p);
                                cell.setColspan(4);
                                //cell.setBackgroundColor(Color.YELLOW);
                                this.skipTable.addCell(cell);

                                cell = new PdfPCell(new Paragraph(result.getTestClass().getName() + "." + result.getMethod().getMethodName()));
                                cell.setColspan(4);
                                this.skipTable.addCell(cell);
                }

                /**
                * @see com.beust.testng.ITestListener#onStart(com.beust.testng.ITestContext)
                */
                public void onStart(ITestContext context) { 
                                log("onStart("+context+")");
                                reportName = System.getProperty("user.dir") + "\\test-output\\PDF_reports\\OrangeTestReport_"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +".pdf";
                                file = new File(reportName);
                                // if file doesn't exists, then create it
                                if (!file.exists()) {
                                                try {
                                                                file.createNewFile();
                                                } catch (IOException e) {
                                                                e.printStackTrace();
                                                }
                                }else if(file.getName().contains("OrangeTestReport_")) {
                                                file.delete();
                                                try {
                                                                file.createNewFile();
                                                } catch (IOException e) {
                                                                e.printStackTrace();
                                                }
                                }

                                try {
                                                PdfWriter.getInstance(this.document, new FileOutputStream(file));
                                } catch (Exception e) {
                                                e.printStackTrace();
                                }
                                this.document.open();

                                Paragraph p = new Paragraph(" OrangeHr Test Automation Result",FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD));
                                addMetaData("OrangeHr Project", "Automation Report", "OrangeHr selenium automation result");
                                try {
                                                this.document.add(p);
                                                this.document.add(new Paragraph(new Date().toString()));
                                } catch (DocumentException e1) {
                                                e1.printStackTrace();
                                }



                }

                /**
                * @see com.beust.testng.ITestListener#onFinish(com.beust.testng.ITestContext)
                */
                public void onFinish(ITestContext context) {

                                log("onFinish("+context+")");

                                if (statTable == null) {
                                                this.statTable = new PdfPTable(new float[]{.3f, .2f, .2f, .2f, .3f});   
                                }

                                if (chartTable == null) {
                                                this.chartTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f});   
                                }

                                Paragraph p = new Paragraph("STATISTICS", new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
                                p.setAlignment(Element.ALIGN_CENTER);
                                PdfPCell cell = new PdfPCell(p);
                                cell.setColspan(5);
                                this.statTable.addCell(cell);

                                cell = new PdfPCell(new Paragraph("Passed"));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Skipped"));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Failed"));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Percent"));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("Total Time"));
                                this.statTable.addCell(cell);

                                int passed = context.getPassedTests().size();
                                int skipped = context.getSkippedTests().size();
                                int failed = context.getFailedTests().size();
                                double total = passed + skipped + failed;
                                double percentPass = ((double)passed/total) * 100;
                                double percentFail = ((double)failed/total) * 100;

                                cell = new PdfPCell(new Paragraph("" + passed));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("" + skipped));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph("" + failed));
                                this.statTable.addCell(cell);
                                cell = new PdfPCell(new Paragraph(new DecimalFormat("#0.00").format(percentPass)));
                                this.statTable.addCell(cell);   
                                cell = new PdfPCell(new Paragraph("" + nbTotalTime));
                                this.statTable.addCell(cell);       


                                DefaultPieDataset dataSet = new DefaultPieDataset();
                                dataSet.setValue("Failed- "+new DecimalFormat("#0.00").format(percentFail)+"%", failed);
                                dataSet.setValue("Skipped", skipped);
                                dataSet.setValue("Passed- "+new DecimalFormat("#0.00").format(percentPass)+"%", passed);
                                p = new Paragraph("Data Chart", new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD));
                                p.setAlignment(Element.ALIGN_CENTER);
                                cell = new PdfPCell(p);
                                cell.setColspan(4);
                                //  cell.setBackgroundColor(Color.LIGHT_GRAY);
                                this.chartTable.addCell(cell);

                                JFreeChart chart = ChartFactory.createPieChart3D(
                                                                "Test Success Rate", dataSet, true, true, false);

                                java.awt.Image originalImage = chart.createBufferedImage(500, 300);
                                Image image1 = null;
                                try {
                                                image1 = Image.getInstance(originalImage,Color.white);
                                } catch (BadElementException e4) {
                                                e4.printStackTrace();
                                } catch (IOException e4) {
                                                e4.printStackTrace();
                                }

                                cell = new PdfPCell(p);
                                cell.setColspan(4);
                                cell.addElement(image1);

                                this.chartTable.addCell(cell);


                                try {
                                                if (this.statTable != null) {
                                                                log("Added Statistics table");
                                                                this.statTable.setSpacingBefore(15f);
                                                                this.document.add(this.statTable);
                                                                this.statTable.setSpacingAfter(15f);
                                                }

                                                if (this.chartTable != null) {
                                                                log("Added chart table");
                                                                this.chartTable.setSpacingBefore(15f);
                                                                this.document.add(this.chartTable);
                                                                this.chartTable.setSpacingAfter(15f);
                                                }
                                                if (this.failTable != null || this.successTable != null) {
                                                                setHeader();
                                                }
                                                if (this.headerofTable != null) {
                                                                log("Added header of table");
                                                                this.headerofTable.setSpacingBefore(15f);
                                                                this.document.add(this.headerofTable);
                                                }
                                                if (this.failTable != null) {
                                                                log("Added fail table");
                                                                this.document.add(this.failTable);
                                                }

                                                if (this.successTable != null) {
                                                                log("Added success table");
                                                                this.document.add(this.successTable);
                                                }

                                                if (this.skipTable != null) {
                                                                log("Added skip table");
                                                                this.document.add(this.skipTable);
                                                }

                                                if (this.failedStep != null || this.successStep != null) {
                                                                setDetailheader();
                                                }
                                                
                                                if (this.detailsSummary != null) {
                                                                this.detailsSummary.setSpacingBefore(20f);
                                                                this.document.add(this.detailsSummary);
                                                }                                              
                                                if (this.failedStep != null) {
                                                                this.document.add(this.failedStep);
                                                }
                                                if (this.successStep != null) {
                                                                log("Added success table");
                                                                this.document.add(this.successStep);
                                                                this.successStep.setSpacingBefore(15f);
                                                }
                                                
                                                this.document.close();

    //                                            ReportMail.sendEmail(reportName);

                                } catch (DocumentException e) {
                                                e.printStackTrace();
                                } catch (Exception e) {
                                                e.printStackTrace();
                                }

                            
                }

                public void addMetaData(String authorName, String title, String description){
                                this.document.addAuthor(authorName);
                                this.document.addTitle(title);
                                this.document.addSubject(description);
                }

                /**
                * log
                * @param o
                */
                public static void log(Object o) {
                                System.out.println("[JyperionListener] " + o);
                }

                public void onTestStart(ITestResult result) {
                                // TODO Auto-generated method stub

                }

                public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
                                // TODO Auto-generated method stub

                }


                public void onExecutionStart() {
                                // TODO Auto-generated method stub

                }


                public void onExecutionFinish() {
                                // TODO Auto-generated method stub

                }


                public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
                                // TODO Auto-generated method stub

                }


                public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
                                // TODO Auto-generated method stub

                }


                public void onStart(ISuite suite) {
                                // TODO Auto-generated method stub

                }


                public void onFinish(ISuite suite) {
                                // TODO Auto-generated method stub

                }
}


