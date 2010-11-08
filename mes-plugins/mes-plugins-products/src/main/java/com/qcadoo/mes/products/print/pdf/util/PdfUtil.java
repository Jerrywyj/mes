package com.qcadoo.mes.products.print.pdf.util;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.qcadoo.mes.api.Entity;
import com.qcadoo.mes.beans.users.UsersUser;
import com.qcadoo.mes.model.types.internal.DateType;

public final class PdfUtil {

    public static final String PDF_EXTENSION = ".pdf";

    private static final String FONT_PATH = "fonts/Arial.ttf";

    private static Font arialBold19Light;

    private static Font arialBold19Dark;

    private static Font arialBold11Dark;

    private static Font arialRegular9Light;

    private static Font arialRegular9Dark;

    private static Font arialBold9Dark;

    private static Font arialRegular10Dark;

    private static Font arialBold10Dark;

    private static Color lineLightColor;

    private static Color lineDarkColor;

    private static Color backgroundColor;

    private static Color lightColor;

    private static BaseFont arial;

    private PdfUtil() {

    }

    public static void prepareFontsAndColors() throws DocumentException, IOException {
        ClassPathResource classPathResource = new ClassPathResource(FONT_PATH);
        FontFactory.register(classPathResource.getPath());
        arial = BaseFont.createFont(classPathResource.getPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        lightColor = new Color(77, 77, 77);
        Color darkColor = new Color(26, 26, 26);
        lineDarkColor = new Color(102, 102, 102);
        lineLightColor = new Color(153, 153, 153);
        backgroundColor = new Color(230, 230, 230);
        arialBold19Light = new Font(arial, 19);
        arialBold19Light.setStyle(Font.BOLD);
        arialBold19Light.setColor(lightColor);
        arialBold19Dark = new Font(arial, 19);
        arialBold19Dark.setStyle(Font.BOLD);
        arialBold19Dark.setColor(darkColor);
        arialRegular9Light = new Font(arial, 9);
        arialRegular9Light.setColor(lightColor);
        arialRegular9Dark = new Font(arial, 9);
        arialRegular9Dark.setColor(darkColor);
        arialBold9Dark = new Font(arial, 9);
        arialBold9Dark.setColor(darkColor);
        arialBold9Dark.setStyle(Font.BOLD);
        arialBold11Dark = new Font(arial, 11);
        arialBold11Dark.setColor(darkColor);
        arialBold11Dark.setStyle(Font.BOLD);
        arialRegular10Dark = new Font(arial, 10);
        arialRegular10Dark.setColor(darkColor);
        arialBold10Dark = new Font(arial, 10);
        arialBold10Dark.setColor(darkColor);
        arialBold10Dark.setStyle(Font.BOLD);
    }

    public static Color getLineDarkColor() {
        return lineDarkColor;
    }

    public static Font getArialBold19Light() {
        return arialBold19Light;
    }

    public static Font getArialBold19Dark() {
        return arialBold19Dark;
    }

    public static Font getArialBold11Dark() {
        return arialBold11Dark;
    }

    public static Font getArialRegular9Light() {
        return arialRegular9Light;
    }

    public static Font getArialRegular9Dark() {
        return arialRegular9Dark;
    }

    public static Font getArialBold9Dark() {
        return arialBold9Dark;
    }

    public static Font getArialRegular10Dark() {
        return arialRegular10Dark;
    }

    public static Font getArialBold10Dark() {
        return arialBold10Dark;
    }

    public static Color getLineLightColor() {
        return lineLightColor;
    }

    public static Color getBackgroundColor() {
        return backgroundColor;
    }

    public static Color getLightColor() {
        return lightColor;
    }

    public static BaseFont getArial() {
        return arial;
    }

    public static void addEndOfDocument(final Document document, final PdfWriter writer, final String text) {
        PdfContentByte cb = writer.getDirectContent();
        cb.saveState();
        cb.setColorFill(lightColor);
        float textBase = document.bottom() - 35;
        float textSize = arial.getWidthPoint(text, 7);
        cb.beginText();
        cb.setFontAndSize(arial, 7);
        cb.setTextMatrix(document.right() - textSize, textBase);
        cb.showText(text);
        cb.endText();
        cb.restoreState();
    }

    public static void addMetaData(final Document document) {
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("QCADOO");
        document.addCreator("QCADOO");
    }

    public static PdfPTable createTableWithHeader(final int numOfColumns, final List<String> header) {
        PdfPTable table = new PdfPTable(numOfColumns);
        table.setWidthPercentage(100f);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setSpacingBefore(7.0f);
        table.getDefaultCell().setBackgroundColor(backgroundColor);
        table.getDefaultCell().setBorderColor(lineDarkColor);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.getDefaultCell().setPadding(5.0f);
        table.getDefaultCell().disableBorderSide(Rectangle.RIGHT);
        int i = 0;
        for (String element : header) {
            i++;
            if (i == header.size()) {
                table.getDefaultCell().enableBorderSide(Rectangle.RIGHT);
            }
            table.addCell(new Phrase(element, arialRegular9Dark));
            if (i == 1) {
                table.getDefaultCell().disableBorderSide(Rectangle.LEFT);
            }
        }
        table.getDefaultCell().setBackgroundColor(null);
        table.getDefaultCell().disableBorderSide(Rectangle.RIGHT);
        table.getDefaultCell().setBorderColor(lineLightColor);
        return table;
    }

    public static void addDocumentHeader(final Document document, final Entity entity, final String documenTitle,
            final String documentAuthor, final Date date, final UsersUser user) throws DocumentException {
        SimpleDateFormat df = new SimpleDateFormat(DateType.DATE_TIME_FORMAT);
        LineSeparator line = new LineSeparator(3, 100f, lineDarkColor, Element.ALIGN_LEFT, 0);
        document.add(Chunk.NEWLINE);
        Paragraph title = new Paragraph(new Phrase(documenTitle, arialBold19Light));
        title.add(new Phrase(" " + entity.getField("name"), arialBold19Dark));
        title.setSpacingAfter(7f);
        document.add(title);
        document.add(line);
        PdfPTable userAndDate = new PdfPTable(2);
        userAndDate.setWidthPercentage(100f);
        userAndDate.setHorizontalAlignment(Element.ALIGN_LEFT);
        userAndDate.getDefaultCell().setBorderWidth(0);
        Paragraph userParagraph = new Paragraph(new Phrase(documentAuthor, arialRegular9Light));
        userParagraph.add(new Phrase(" " + user.getUserName(), arialRegular9Dark));
        Paragraph dateParagraph = new Paragraph(df.format(date), arialRegular9Light);
        userAndDate.addCell(userParagraph);
        userAndDate.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        userAndDate.addCell(dateParagraph);
        document.add(userAndDate);
    }

    public static void addTableCellAsTable(final PdfPTable table, final String label, final Object fieldValue,
            final String nullValue, final Font headerFont, final Font valueFont) {
        PdfPTable cellTable = new PdfPTable(1);
        cellTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        cellTable.addCell(new Phrase(label, headerFont));
        Object value = fieldValue;
        if (value == null) {
            cellTable.addCell(new Phrase(nullValue, valueFont));
        } else {
            cellTable.addCell(new Phrase(value.toString(), valueFont));
        }
        table.addCell(cellTable);
    }
}
