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
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.gedantic.analyzer.AResult;
import org.gedantic.analyzer.IAnalyzer;
import org.gedantic.analyzer.result.FamilyRelatedResult;
import org.gedantic.analyzer.result.IndividualRelatedResult;
import org.gedantic.analyzer.result.RelationshipRelatedResult;
import org.gedantic.analyzer.result.SourceRelatedResult;
import org.gedcom4j.model.Individual;
import org.gedcom4j.relationship.SimpleRelationship;

/**
 * Class for creating an Excel workbook from the results.
 *
 * @author frizbog
 */
public class WorkbookCreator {

    /** The results. */
    private final List<? extends AResult> results;

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

    /** The has individuals. */
    private boolean hasIndividuals;

    /** The has families. */
    private boolean hasFamilies;

    /** The has sources. */
    private boolean hasSources;

    /** The has relationships. */
    private boolean hasRelationships;

    /**
     * Instantiates a new workbook creator.
     *
     * @param analyzer
     *            the analyzer
     * @param results
     *            the results
     */
    public WorkbookCreator(IAnalyzer analyzer, List<? extends AResult> results) {
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
        if (hasFamilies) {
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Person 1");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Person 2");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Fact");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Value");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Problem");
        } else if (hasIndividuals) {
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Individual");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Fact");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Value");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Problem");
        } else if (hasRelationships) {
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Individual");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Fact");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Person 1");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Has");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Person 2");

            nextCol();
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Problem");
        } else if (hasSources) {
            cell.setCellStyle(styleHeader);
            cell.setCellValue("Source");

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

    }

    /**
     * Adds the rows of data.
     */
    private void addRowsOfData() {
        for (AResult r : results) {
            nextRow();
            if (r instanceof IndividualRelatedResult) {
                IndividualRelatedResult irr = (IndividualRelatedResult) r;
                cell.setCellValue(irr.getIndividual().getFormattedName());

                if (hasFamilies) {
                    // Skip second person
                    nextCol();
                }

                nextCol();
                cell.setCellValue(irr.getFactType());

                nextCol();
                if (irr.getValue() instanceof Individual) {
                    Individual i = (Individual) irr.getValue();
                    cell.setCellValue(i.getFormattedName());
                    sheet.getRow((short) 3).getCell((short) 2).setCellValue("Other Person");
                } else {
                    if (irr.getValue() != null) {
                        cell.setCellValue(StringEscapeUtils.unescapeHtml(irr.getValue().toString()));
                    }
                }

                nextCol();
                cell.setCellValue(irr.getProblem());

            } else if (r instanceof FamilyRelatedResult) {
                FamilyRelatedResult frr = (FamilyRelatedResult) r;
                Individual husband = frr.getFamily().getHusband();
                Individual wife = frr.getFamily().getWife();

                if (husband != null) {
                    cell.setCellValue(husband.getFormattedName());
                }

                nextCol();
                if (wife != null) {
                    cell.setCellValue(wife.getFormattedName());
                }

                nextCol();
                cell.setCellValue(frr.getFactType());

                nextCol();
                if (frr.getValue() != null) {
                    if (frr.getValue() instanceof Individual) {
                        Individual i = (Individual) frr.getValue();
                        cell.setCellValue(i.getFormattedName());
                        sheet.getRow((short) 3).getCell((short) 3).setCellValue("Other Person");
                    } else if (frr.getValue() instanceof Set<?>) {
                        @SuppressWarnings("unchecked")
                        Set<Individual> others = (Set<Individual>) frr.getValue();
                        short c = colNum;
                        int j = 0;
                        for (Individual i : others) {
                            cell.setCellValue(i.getFormattedName());
                            sheet.getRow((short) 3).getCell((short) 3).setCellValue("Other People");
                            if (j == 0) {
                                nextCol();
                                cell.setCellValue(frr.getProblem());
                            }
                            j++;
                            if (j < others.size()) {
                                nextRow();
                                for (int k = 0; k < c; k++) {
                                    nextCol();
                                }
                            }
                        }
                    } else {
                        cell.setCellValue(StringEscapeUtils.unescapeHtml(frr.getValue().toString()));
                    }
                }
            } else if (r instanceof RelationshipRelatedResult) {
                RelationshipRelatedResult rrr = (RelationshipRelatedResult) r;
                cell.setCellValue(rrr.getIndividual().getFormattedName());

                nextCol();
                cell.setCellValue(rrr.getFactType());

                nextCol();
                @SuppressWarnings("unchecked")
                List<List<SimpleRelationship>> path = (List<List<SimpleRelationship>>) rrr.getValue();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < path.size(); i++) {
                    List<SimpleRelationship> chain = path.get(i);
                    for (int j = 0; j < chain.size(); j++) {
                        SimpleRelationship sr = chain.get(j);

                        cell.setCellValue(sr.getIndividual1().getFormattedName());
                        nextCol();
                        if (sr.getName() == null) {
                            cell.setCellValue("UNKNOWN");
                        } else {
                            cell.setCellValue(sr.getName().toString());
                        }
                        nextCol();
                        cell.setCellValue(sr.getIndividual2().getFormattedName());
                        if (i == 0) {
                            nextCol();
                            cell.setCellValue(rrr.getProblem());
                        }
                        if (i < path.size()) {
                            nextRow();
                            nextCol();
                            nextCol();
                        }
                    }
                }

            } else if (r instanceof SourceRelatedResult) {
                SourceRelatedResult srr = (SourceRelatedResult) r;
                cell.setCellValue(srr.getSource().getTitle().get(0));

                nextCol();
                cell.setCellValue(srr.getFactType());

                nextCol();
                if (srr.getValue() != null) {
                    cell.setCellValue(StringEscapeUtils.unescapeHtml(srr.getValue().toString()));
                }

                nextCol();
                cell.setCellValue(srr.getProblem());
            }
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

        for (AResult r : results) {
            if (r instanceof IndividualRelatedResult) {
                hasIndividuals = true;
            } else if (r instanceof FamilyRelatedResult) {
                hasFamilies = true;
            } else if (r instanceof RelationshipRelatedResult) {
                hasRelationships = true;
            } else if (r instanceof SourceRelatedResult) {
                hasSources = true;
            } else {
                throw new IllegalStateException("Unknown result type " + r.getClass().getName());
            }
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
