package thescore.reports;

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

import thescore.classes.TeamPerformance;
import thescore.model.Match;

public class MatchResultPDFBuilder extends AbstractPDFView {

	private PdfPCell defaultCell = new PdfPCell();
	
	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<Integer, TeamPerformance> teamPerformances = (Map<Integer, TeamPerformance>) model.get("teamPerformances");
		Match match = (Match) model.get("match");
		
		Integer teamAId = match.getTeamA().getId();
		Integer teamBId = match.getTeamB().getId();
		
		TeamPerformance teamPerformanceA = teamPerformances.get(teamAId);
		TeamPerformance teamPerformanceB = teamPerformances.get(teamBId);
		
		document.add(new Paragraph(match.getMatchUp()));
		
		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 2.0f, 3.0f, 2f });
		table.setSpacingBefore(10);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
         
		defaultCell = new PdfPCell();
		defaultCell.setPadding(5);
		defaultCell.setBorderColor(BaseColor.LIGHT_GRAY);
         
		defaultCell.setPhrase(new Phrase(match.getTeamA().getDisplayString(), font));
        table.addCell(defaultCell);
        
        defaultCell.setPhrase(new Phrase("VS.", font));
        table.addCell(defaultCell);
        
        defaultCell.setPhrase(new Phrase(match.getTeamB().getDisplayString(), font));
        table.addCell(defaultCell);
        
        table = addCell(table, match.getTeamATimeout());
        table = addCell(table, "Timeouts");
        table = addCell(table, match.getTeamBTimeout());
        
        table = addCell(table, teamPerformanceA.getFg() + " / " + teamPerformanceA.getFga());
        table = addCell(table, "Field Goals");
        table = addCell(table, teamPerformanceB.getFg() + " / " + teamPerformanceB.getFga());
        
        table = addCell(table, teamPerformanceA.getFgPercent());
        table = addCell(table, "Field Goal %");
        table = addCell(table, teamPerformanceB.getFgPercent());
        
        table = addCell(table, teamPerformanceA.getThreefg() + " / " + teamPerformanceA.getThreefga());
        table = addCell(table, "3 Pointers");
        table = addCell(table, teamPerformanceB.getThreefg() + " / " + teamPerformanceB.getThreefga());
        
        table = addCell(table, teamPerformanceA.getThreeFgPercent());
        table = addCell(table, "3 Pointer %");
        table = addCell(table, teamPerformanceB.getThreeFgPercent());
        
        table = addCell(table, teamPerformanceA.getFt() + " / " + teamPerformanceA.getFta());
        table = addCell(table, "Free Throws");
        table = addCell(table, teamPerformanceB.getFt() + " / " + teamPerformanceB.getFta());
        
        table = addCell(table, teamPerformanceA.getFtPercent());
        table = addCell(table, "Free Throw %");
        table = addCell(table, teamPerformanceB.getFtPercent());
        
        table = addCell(table, teamPerformanceA.getAst());
        table = addCell(table, "Assists");
        table = addCell(table, teamPerformanceB.getAst());
        
        table = addCell(table, teamPerformanceA.getTo());
        table = addCell(table, "Turnovers");
        table = addCell(table, teamPerformanceB.getTo());
        
        table = addCell(table, teamPerformanceA.getFoul());
        table = addCell(table, "Team Fouls");
        table = addCell(table, teamPerformanceB.getFoul());
        
        table = addCell(table, teamPerformanceA.getTotalRebounds());
        table = addCell(table, "Total Rebounds");
        table = addCell(table, teamPerformanceB.getTotalRebounds());
        
        table = addCell(table, teamPerformanceA.getOff());
        table = addCell(table, "Off Rebounds");
        table = addCell(table, teamPerformanceB.getOff());
        
        table = addCell(table, teamPerformanceA.getStl());
        table = addCell(table, "Steals");
        table = addCell(table, teamPerformanceB.getStl());
        
        table = addCell(table, teamPerformanceA.getBlk());
        table = addCell(table, "Blocks");
        table = addCell(table, teamPerformanceB.getBlk());
        
        document.add(table);
	}
	
	private PdfPTable addCell(PdfPTable table, Object data){
		defaultCell.setPhrase(new Phrase(String.valueOf(data)));
		table.addCell(defaultCell);
		return table;
	}

}
