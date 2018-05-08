package mujava.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import mujava.cli.testnew;

public class MujavaRunner2 {

	public static void main(String[] args) throws Exception {
		String sessionName = "session2";
		String sourceDirectory = "/media/selamic/LENOVO/mydocs/loqback-mutationtesting/loqback-mutationtesting/logback-core/src/main/java";
		File f = new File(sessionName);
		if(f.exists())
		{
			FileUtils.deleteDirectory(f);
		}
		
		Iterator<File> iterateFiles = 
					FileUtils.iterateFiles(new File(sourceDirectory), 
							new RegexFileFilter("[a-zA-Z0-9]{1,}\\.java"), TrueFileFilter.INSTANCE);
		List<String> fileNames = new ArrayList<String>();
		while(iterateFiles.hasNext()) {
			File next = iterateFiles.next();
//			System.out.println(next.getAbsolutePath());
			if(next.isDirectory())
				continue;
			fileNames.add(next.getAbsolutePath());
		}
//		fileNames.stream().forEach((fname)->{
//			System.out.println(fname);
//		});
		
		int i = 0;
		String[] testnewargs = new String[fileNames.size()+1];
		testnewargs[i] = sessionName;
		System.out.println(fileNames.size() +" " + testnewargs.length); 
		i++;
		for (int j=0; j < fileNames.size();) {
			testnewargs[i] = fileNames.get(j);
			i++;
			j++;
		}
		System.out.println(Arrays.toString(testnewargs));
		List<String> ignoreTests = Arrays.asList("AllTest.java","PackageTest.java");
		testnew.main(testnewargs);
	}
	
	
	private void log(String str) {
		System.out.println(str);
	}
}
