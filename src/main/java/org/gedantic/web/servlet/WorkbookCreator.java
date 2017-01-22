/*
 * Copyright (c) 2016 Matthew R. Harrah
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package org.gedantic.web.servlet;

import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gedantic.analyzer.AnalysisResult;
import org.gedantic.analyzer.IAnalyzer;

/**
 * Class for creating an Excel workbook from the results.
 *
 * @author frizbog
 */
public class WorkbookCreator {

    /** The results. */
    private final List<AnalysisResult> results;

    /** The analyzer. */
    private final IAnalyzer analyzer;

    /** The workbook. */
    private Workbook wb;

    /** The sheet. */
    private Sheet sheet;

    /** The row num. */
    private short rowNum = -1;

    /** The row. */
    private Row row;

    /** The col num. */
    private short colNum = -1;

    /** The max col num. */
    private short maxColNum = -1;

    /** The cell. */
    private Cell cell;

    /** The style title. */
    private CellStyle styleTitle;

    /** The style subtitle. */
    private CellStyle styleSubtitle;

    /** The style normal. */
    private CellStyle styleNormal;

    /** The style header. */
    private CellStyle styleHeader;

    /** The style hyperlink. */
    private CellStyle styleHyperlink;

    /**
     * Instantiates a new workbook creator.
     *
     * @param analyzer
     *            the analyzer
     * @param results
     *            the results
     */
    public WorkbookCreator(IAnalyzer analyzer, List<AnalysisResult> results) {
        this.analyzer = analyzer;
        this.results = results;
    }

    /**
     * Creates the workbook
     *
     * @return the workbook
     */
    public Workbook create() {
        wb = new XSSFWorkbook();
        setupStyles();
        sheet = wb.createSheet(WorkbookUtil.createSafeSheetName(analyzer.getName()));

        setupStyles();

        addTitleAndSubTitle();

        inspectResults();

        addHeaders();
        addRowsOfData();
        addFooter();

        autofitColumns();

        return wb;
    }

    /**
     * Adds the footer.
     */
    private void addFooter() {
        nextRow();
        nextRow();
        cell.setCellValue("Produced by gedantic");

        Hyperlink link = wb.getCreationHelper().createHyperlink(org.apache.poi.common.usermodel.Hyperlink.LINK_URL);
        link.setAddress("http://gedantic.org");
        cell.setHyperlink(link);

        cell.setCellStyle(styleHyperlink);

    }

    /**
     * Adds the headers.
     */
    private void addHeaders() {
        nextRow();
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Item Type");

        nextCol();
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Item");

        nextCol();
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Fact");

        nextCol();
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Value");

        nextCol();
        cell.setCellStyle(styleHeader);
        cell.setCellValue("Problem");

    }

    /**
     * Adds the rows of data.
     */
    private void addRowsOfData() {
        for (AnalysisResult r : results) {
            nextRow();
            cell.setCellValue(r.getTypeOfItemWithIssue());

            nextCol();
            cell.setCellValue(r.getProblematicValue());

            nextCol();
            cell.setCellValue(r.getAspectOfItemWithIssue());

            nextCol();
            cell.setCellValue(r.getProblemDescription());
        }

    }

    /**
     * Adds the title and sub title.
     */
    private void addTitleAndSubTitle() {
        nextRow();
        cell.setCellValue(analyzer.getName());
        cell.setCellStyle(styleTitle);

        nextRow();
        cell.setCellValue(analyzer.getDescription());
        cell.setCellStyle(styleSubtitle);

        nextRow();
    }

    /**
     * Autofit columns.
     */
    private void autofitColumns() {
        sheet.addMergedRegion(new CellRangeAddress(0, 0, (short) 0, maxColNum));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, (short) 0, maxColNum));
        for (int i = 0; i <= maxColNum; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Inspect results.
     */
    private void inspectResults() {
        if (results == null || results.isEmpty()) {
            cell.setCellValue("No results found.");
            return;
        }
    }

    /**
     * Advance to next column
     */
    private void nextCol() {
        cell = row.createCell(++colNum);
        if (colNum >= maxColNum) {
            maxColNum = colNum;
        }
        cell.setCellStyle(styleNormal);
    }

    /**
     * Advance to next row.
     */
    private void nextRow() {
        row = sheet.createRow(++rowNum);
        colNum = -1;
        nextCol();
    }

    /**
     * Setup styles.
     */
    private void setupStyles() {
        styleTitle = wb.createCellStyle();
        Font title_font = wb.createFont();
        title_font.setFontName("Helvetica");
        title_font.setColor(IndexedColors.BLACK.getIndex());
        title_font.setFontHeightInPoints((short) 24);
        styleTitle.setFont(title_font);

        styleSubtitle = wb.createCellStyle();
        Font subtitle_font = wb.createFont();
        subtitle_font.setFontName("Helvetica");
        subtitle_font.setColor(IndexedColors.GREY_50_PERCENT.getIndex());
        subtitle_font.setFontHeightInPoints((short) 18);
        styleSubtitle.setFont(subtitle_font);

        styleHyperlink = wb.createCellStyle();
        Font hlink_font = wb.createFont();
        hlink_font.setFontName("Helvetica");
        hlink_font.setUnderline(Font.U_SINGLE);
        hlink_font.setColor(IndexedColors.BLUE.getIndex());
        styleHyperlink.setFont(hlink_font);

        styleNormal = wb.createCellStyle();
        Font normal_font = wb.createFont();
        normal_font.setFontName("Helvetica");
        normal_font.setColor(IndexedColors.BLACK.getIndex());
        normal_font.setFontHeightInPoints((short) 12);
        styleNormal.setFont(normal_font);
        styleNormal.setWrapText(true);

        styleHeader = wb.createCellStyle();
        Font header_font = wb.createFont();
        header_font.setFontName("Helvetica");
        header_font.setColor(IndexedColors.WHITE.getIndex());
        header_font.setBold(true);
        header_font.setFontHeightInPoints((short) 12);
        XSSFColor bg = new XSSFColor(new java.awt.Color(0x28, 0x60, 0x90));
        ((XSSFCellStyle) styleHeader).setFillForegroundColor(bg);
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleHeader.setFont(header_font);

    }

}
