package mujava.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import mujava.cli.testnew;

public class MujavaRunner {

	public static String MUJAVA_PATH = "/media/selamic/LENOVO/mydocs/loqback-mutationtesting/"
			+ "loqbackmutationtestingws2/mujavaexperiment/libs";
	public static String workspace = "/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqback-mutationtesting/logback-core";
	public static String MUJAVA_JAR = "/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqbackmutationtestingws1/utils/libs/mujava.jar";
	public static String MUTANT_GENERATOR_UI = "mujava.gui.GenMutantsMain";

	public static void main(String[] args) throws Exception {
		MujavaRunner runner = new MujavaRunner();
		runner.createTestSession();
		//// runner.generateMutants();
		//// runner.copySources();
		// runner.runMutationTests();
		// System.out.println(Arrays.toString(args));
		// args = new String[2];
		// args[0] = "ch.qos.logback.core.appender.ConsoleAppenderTest";
		// args[1] = "session1";
		// runmutes.main(args);
	}

	private List<String> getClasses() {
		String sourceDirectory = "/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqback-mutationtesting/logback-core/src/main";
		Iterator<File> iterateFiles = FileUtils.iterateFiles(new File(sourceDirectory),
				new RegexFileFilter("[a-zA-Z0-9]{1,}\\.java"), TrueFileFilter.INSTANCE);
		List<String> fileNames = new ArrayList<String>();
		while (iterateFiles.hasNext()) {
			File next = iterateFiles.next();
			// System.out.println(next.getAbsolutePath());
			if (next.isDirectory())
				continue;
			fileNames.add(next.getAbsolutePath());
		}
		return fileNames;
	}

	private void log(String str) {
		System.out.println(str);
	}

	private String exportClasses() throws Exception {
		StringBuilder cmdBuilder = new StringBuilder();
		cmdBuilder.append(MUJAVA_JAR).append(":");
		cmdBuilder.append(MUJAVA_PATH + "/" + "commons-io-2.4.jar").append(":");
		cmdBuilder.append(MUJAVA_PATH + "/" + "openjava.jar").append(":");
		cmdBuilder.append("/usr/lib/jvm/java-8-oracle/lib/tools.jar").append(":");
		cmdBuilder.append("/home/selamic/.m2/repository/junit/junit/4.12/junit-4.12.jar").append(":");
		cmdBuilder.append("/home/selamic/.m2/repository/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar");
		return cmdBuilder.toString();
	}

	private static class ProcessListener implements Runnable {

		InputStream is = null;

		public ProcessListener(InputStream is) {
			this.is = is;
		}

		@Override
		public void run() {
			InputStreamReader iserr = new InputStreamReader(this.is);
			BufferedReader br = new BufferedReader(iserr);

			String line = null;

			try {
				while ((line = br.readLine()) != null) {
					System.out.println(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private void processOutput(Process process) throws IOException, InterruptedException {
		new Thread(new ProcessListener(process.getInputStream())).start();
		;
		new Thread(new ProcessListener(process.getErrorStream())).start();
		;

		int exitVal = process.waitFor();
		System.out.println("Process exitValue: " + exitVal);
	}

	private void createTestSession() throws Exception {
		copySources();
		StringBuilder cmdBuilder = new StringBuilder();
		cmdBuilder.append("java").append(" -cp ");
		cmdBuilder.append(exportClasses()).append(" ");
		cmdBuilder.append("mujava.cli.testnew").append(" ");
		cmdBuilder.append("session1").append(" ");
		for (String fName : getClasses()) {
			cmdBuilder.append(fName).append(" ");
		}
		log(cmdBuilder.toString());
		Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
		processOutput(process);
	}

	private void copyJavaFiles(String root, String dest) throws Exception {
		FileUtils.copyDirectory(new File(root), new File(dest));
	}

	private void copySources() throws Exception {
		copyJavaFiles(
				"/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqback-mutationtesting/logback-core/src/main/java",
				"/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqbackmutationtestingws1/utils/session1/src/");
//		copyJavaFiles(
//				"/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqback-mutationtesting/logback-core/target/classes",
//				"/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqbackmutationtestingws1/utils/session1/classes");
	}

	/**
	 * generate method level mutations if no mutant operators are given, mujava
	 * generate mutants for all mutation operators
	 * 
	 * @throws Exception
	 */
	private void generateMutants() throws Exception {
		StringBuilder cmdBuilder = new StringBuilder();
		cmdBuilder.append("java").append(" -cp ");
		cmdBuilder.append(exportClasses()).append(" ");
		cmdBuilder.append("mujava.cli.genmutes").append(" -all ");
		cmdBuilder.append("session1").append(" ");
		log(cmdBuilder.toString());
		Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
		processOutput(process);
	}

	private void runMutationTests() throws Exception {
		copyJavaFiles(
				"/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqback-mutationtesting/logback-core/target/test-classes",
				"/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqbackmutationtestingws1/utils/session1/testset/");
		StringBuilder cmdBuilder = new StringBuilder();
		cmdBuilder.append("java").append(" -cp ");
		cmdBuilder.append(exportClasses()).append(" ");
		cmdBuilder.append("mujava.cli.runmutes").append(" -default ");
		cmdBuilder.append("ch.qos.logback.core.appender.ConsoleAppenderTest").append(" ");
		cmdBuilder.append("session1").append(" ");
		log(cmdBuilder.toString());
		Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
		processOutput(process);
	}
}
