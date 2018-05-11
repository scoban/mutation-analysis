package cli.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author selamic
 * currently not working
 * use command line 
 */
public class JudyRunner {

	public static String JUDY_PATH = "judy_path";

	public static void main(String[] args) {
		String workspace = "path-to-workspace";
		String testPath = workspace + "/judy";
		String classPath = workspace + "/judy";
		String sourcePath = workspace + "/judy/src";
		String libs = workspace + "/lib";
		String resultPath = "judyresult.json";
//		new JudyRunner().runJudyHelp();
		new JudyRunner().runTestSuites(testPath, classPath, libs, sourcePath);
	}

	public void runTestSuites(String testPath, String classPath, String libs, String sourcePath) {
		try {
			StringBuilder cmdBuilder = new StringBuilder();
			cmdBuilder.append(JUDY_PATH).append(" ");
			cmdBuilder.append("-p").append(" ");
			cmdBuilder.append(classPath).append(" ");
			cmdBuilder.append("--test-files").append(" ");//cmdBuilder.append("--test-files").append(" ");
			cmdBuilder.append(testPath).append(" ");
			cmdBuilder.append("-l").append(" ");//cmdBuilder.append("--libraries").append(" ");
			cmdBuilder.append(libs).append(" ");
			System.out.println(cmdBuilder.toString());
//			Process process = Runtime.getRuntime().exec(cmdBuilder.toString());
//			System.out.println("running test...");
//			processOutput(process);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processOutput(Process process) throws IOException, InterruptedException {
		InputStream stdin = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(stdin);
		BufferedReader br = new BufferedReader(isr);

		String line = null;
		
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		int exitVal = process.waitFor();
		System.out.println("Process exitValue: " + exitVal);
	}

	public void runJudyHelp() {
		try {
			Process process = Runtime.getRuntime().exec(JUDY_PATH + " --help");
			System.out.println("running judy help...");
			processOutput(process);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
