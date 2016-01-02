package thescore.reports;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import thescore.model.Player;

public class PlayerListPDFBuilder extends AbstractPDFView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Player> players = (List<Player>) model.get("players");
        
		document.add(new Paragraph("Player List"));
         
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {2.0f, 3.0f, 1.5f, 1.5f});
        table.setSpacingBefore(10);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);
         
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.GRAY);
        cell.setPadding(5);
         
//      cell.setPhrase(new Phrase("Image", font));
//      table.addCell(cell);
        
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Team", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Contact No.", font));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Email.", font));
        table.addCell(cell);
         
        for (Player player : players) {
        	table.addCell(player.getDisplayString());
//        	table.addCell(Image.getInstance(player.getImage()));
        	table.addCell(player.getTeam().getDisplayString());
        	table.addCell(player.getContactNo());
        	table.addCell(player.getEmail());
        }
         
        document.add(table);
	}

}
