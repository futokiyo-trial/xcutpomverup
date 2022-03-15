package io.yoshizaki2104.xcutpomverup.cli.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import io.yoshizaki2104.xcutpomverup.cli.config.ProjectDef;
import io.yoshizaki2104.xcutpomverup.cli.config.PropertyDef;
import io.yoshizaki2104.xcutpomverup.cli.config.XcutPomVerupConfig;
import io.yoshizaki2104.xcutpomverup.cli.utils.JsonUtils;
import io.yoshizaki2104.xcutpomverup.cli.utils.ReadUtils;

public class XcutPomVerupApp {

	public static void main(String[] args) {
		String configFilePath = args[0];
		try {
			XcutPomVerupConfig config = convertToConfig(configFilePath);
			List<PropertyDef> properties = config.getProperties();
			
			List<ProjectDef> projects = config.getProjects();
			XcutPomVerupApp self = new XcutPomVerupApp();
			self.replaceProjectsVersion(projects, properties);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
	
	static XcutPomVerupConfig convertToConfig(String path) throws FileNotFoundException, IOException {
		
		String xmlContent = ReadUtils.readFrom(path);
		
		XcutPomVerupConfig config = JsonUtils.parse(XcutPomVerupConfig.class, xmlContent);
		return config;
	}
	
	static String replaceLine(String originalLine, int lineNo, ProjectDef project, List<PropertyDef> properties) {
		String line = originalLine;
		if(lineNo < 10) {
			line = line.replace("<version>"+project.getVersionSrc()+"</version>", "<version>"+project.getVersionDest()+"</version>");
		}
		
		for(PropertyDef propertyDef : properties) {
			String srcPropElement = "<"+propertyDef.getPropertyName()+">"
									+ propertyDef.getVersionSrc()
									+ "</"+propertyDef.getPropertyName()+">";
			String destPropElement = "<"+propertyDef.getPropertyName()+">"
					+ propertyDef.getVersionDest()
					+ "</"+propertyDef.getPropertyName()+">";
			line = line.replace(srcPropElement, destPropElement);
		}
		return line ;
	}
	
	void replaceProjectsVersion(List<ProjectDef> projects, List<PropertyDef> properties) {
		
		Predicate<? super Path> isPomFile= path -> {
			if(path.getFileName()!=null) {
				return "pom.xml".equals(path.getFileName().toString());
			}
			return false;
		};
		
		projects.forEach(project -> {

			try {
				Files.walk(Paths.get(project.getRootDirectory()))
				.filter(isPomFile)
				.forEach(pomPath -> {
					System.out.println("-----");
					replaceProjectVersion(pomPath, project, properties);
					System.out.println("=====");
				});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
	
	void replaceProjectVersion(Path pomPath, ProjectDef project, List<PropertyDef> properties)  {
		
		Path backupPath = Paths.get(pomPath.toAbsolutePath().toString()+".org_"+System.currentTimeMillis());
		System.out.println("backup " + pomPath.toAbsolutePath() + " -> " + backupPath);
		try {
			Files.copy(pomPath, backupPath);
			String tmpFilePath = pomPath.toAbsolutePath().toString()+".tmp";
			String lineBreak = ReadUtils.determineLineBreak(pomPath.toAbsolutePath().toString());
			try(FileInputStream fis = new FileInputStream(pomPath.toAbsolutePath().toString());
				InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr);
				FileOutputStream fos = new FileOutputStream(tmpFilePath);
				OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				BufferedWriter bw = new BufferedWriter(osr);) {
				String line = null;
				int lineNo = 0;
				while((line = br.readLine())!=null) {
					lineNo++;
					String replacedLine = replaceLine(line, lineNo, project, properties);
					bw.append(replacedLine);
					bw.append(lineBreak);
					if(!line.equals(replacedLine)) {
						System.out.println(project.getProjectName() + " : " + line + " -> " + replacedLine);
					}
				}
				bw.flush();
			}
			
			File deletingFile = new File(pomPath.toAbsolutePath().toString());
			deletingFile.delete();
			System.out.println("before file [" + pomPath.toAbsolutePath() + "] deleted.");
			
			Path tmpPath = Paths.get(tmpFilePath);
			Files.copy(tmpPath, pomPath);
			System.out.println("Copied " + tmpPath + " -> " + pomPath);
			
			File deletingTmpFile = new File(tmpPath.toAbsolutePath().toString());
			deletingTmpFile.delete();
			System.out.println("tmp file [" + tmpPath.toAbsolutePath() + "] deleted.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
