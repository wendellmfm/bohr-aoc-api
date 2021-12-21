package br.ufc.mdcc.bohr.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import br.ufc.mdcc.bohr.model.AoCInfo;
import br.ufc.mdcc.bohr.model.AoCSuite;

public class ReportGenerator {
	private final static String REPORT_PATH = "./atomsreports";

	public static void generateCSVFile(Collection<AoCSuite> aocSuiteList) {
		ArrayList<List<String>> rows = new ArrayList<>();
		
		for (AoCSuite suite : aocSuiteList) {
			for (AoCInfo aocInfo : suite.getAtomsOfConfusion()) {
				String snippet = aocInfo.getSnippet();
				snippet = snippet.replaceAll("\\r\\n|\\r|\\n|\\t", " ");
				
				if(snippet.contains(",")) {
					snippet = snippet.replaceAll(",", "");
					aocInfo.setSnippet(snippet);
				}
				
				rows.add(Arrays.asList(suite.getClassQualifiedName(),
						aocInfo.getAtomOfConfusion().getFullName(),
						snippet,
						String.valueOf(aocInfo.getLineNumber())));
			}
		}
		
		writeCSV(rows);
	}

	private static void writeCSV(ArrayList<List<String>> rows) {
		createReportDirectory();
		
		String fullReportName = getReportName().toString();
		
		FileWriter csvWriter = null;
		try {
			csvWriter = new FileWriter(REPORT_PATH + "/" + fullReportName);
			csvWriter.append("Class");
			csvWriter.append(",");
			csvWriter.append("Atom");
			csvWriter.append(",");
			csvWriter.append("Snippet");
			csvWriter.append(",");
			csvWriter.append("Line");
			csvWriter.append("\n");
			
			for (List<String> rowData : rows) {
				csvWriter.append(String.join(",", rowData));
				csvWriter.append("\n");
			}
			
			csvWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (csvWriter != null) {
				try {
					csvWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void createReportDirectory() {
		File directory = new File(REPORT_PATH);
		if (!directory.exists()) {
			directory.mkdir();
		}
	}

	private static StringBuffer getReportName() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		dateFormat.setTimeZone(calendar.getTimeZone());
		
		StringBuffer reportName = new StringBuffer("atoms-report-");
		reportName.append(dateFormat.format(calendar.getTime()));
		reportName.append(".csv");
		return reportName;
	}
}
