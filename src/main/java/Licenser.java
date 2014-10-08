/*
 *OpenCraft - Build your own open world!
 *
 *Author : HyeonuPark(Moonrise1275)
 *
 *--------------------------------------------------------------------------
 *
 *The MIT License (MIT)
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 *
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Licenser {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Format : java Licenser.class <projectDir> <author>");
			return;
		}
		File rootDir = new File(args[0]);
		File targetDir = new File(rootDir, "src");
		File licenseDir = new File(rootDir, "License.txt");
		String author = args[1];
		allFile(targetDir, licenseDir, author);
		System.out.println("All done!");
	}
	
	public static void allFile(File dir, File license, String author) throws IOException {
		if (!dir.isDirectory()) return;
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				allFile(file, license, author);
			} else if (file.getName().endsWith(".java")) {
				process(file, license, author);
				//delete(file);
			}
		}
	}
	
	public static void process(File file, File license, String author) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String firstLine = reader.readLine();
		if (firstLine.equals("/*")) {
			reader.close();
			return;
		}
		
		File tmp = new File(file.getAbsolutePath() + ".tmp");
		tmp.createNewFile();
		BufferedReader lic = new BufferedReader(new FileReader(license));
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(tmp)));
		
		writer.println("/*");
		writer.println(" *OpenCraft - Build your own open world!");
		writer.println(" *");
		writer.println(" *Author : " + author);
		writer.println(" *");
		writer.println(" *--------------------------------------------------------------------------");
		
		while (true) {
			String line = lic.readLine();
			if (line == null) break;
			writer.println(line);
		}
		lic.close();
		writer.println(" */");
		writer.println();
		writer.println(firstLine);
		while (true) {
			String line = reader.readLine();
			if (line == null) break;
			writer.println(line);
		}
		reader.close();
		writer.close();
		file.delete();
		tmp.renameTo(file);
		
		System.out.println("done - " + file.getName());
	}
	
	public static void delete(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		File tmp = new File(file.getAbsolutePath() + ".tmp");
		tmp.createNewFile();
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(tmp)));
		
		while (true) {
			String line = reader.readLine();
			if (line == null) break;
			if (!(line.startsWith("/*") || line.startsWith(" *") || line.startsWith("*/") || line.isEmpty())) {
				writer.println(line);
				break;
			};
		}
		
		while (true) {
			String line = reader.readLine();
			if (line == null) break;
			writer.println(line);
		}
		
		reader.close();
		writer.close();
		file.delete();
		tmp.renameTo(file);
		
		System.out.println("done - " + file.getName());
	}
}
