package cn.hsnss;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;

import java.io.*;
import java.util.*;

public class Main {

	static Queue<String> RAR_LIST = new LinkedList<String>();

	private static void doTrav(String p, ArrayList<String> list) {
		File f = new File(p);
		if (f.exists()) {
			if (f.isDirectory()) {
				for (File file : f.listFiles()) {
					doTrav(file.getPath(), list);
				}
			} else {
				list.add(p);
			}
		}
	}
	private static String[] doTrav(String p) {
		ArrayList<String> list = new ArrayList<String>();
		doTrav(p, list);
		return list.toArray(new String[list.size()]);
	}
	static Msg doRAR(String rar) throws IOException, RarException {
		Msg msg;
		Archive arc = new Archive(new File(rar));

		List<FileHeader> list = arc.getFileHeaders();
		for (int i = 0; i < list.size(); i++)
			if (list.get(i).isDirectory())
				list.remove(i--);
		msg = new Msg(rar, list.size());
		for (FileHeader f : list) {
			InputStream is = arc.getInputStream(f);
			long size = f.getFullUnpackSize();
			String name = f.isUnicode() ? f.getFileNameW() : f.getFileNameString();
			msg.add(FileType.judge(is, size), name);
		}
		return msg;
	}
	static Msg doDIR(String path) throws IOException, RarException {
		Msg msg;
		String[] list = doTrav(path);
		msg = new Msg(path, list.length);
		for (String item : list) {
			if (!item.endsWith("rar")) {
				File f = new File(item);
				msg.add(FileType.judge(new FileInputStream(f), f.length()), f.getAbsolutePath());
			} else if (item.endsWith("rar")) {
				RAR_LIST.offer(item);
			}
		}
		return msg;
	}
	public static void main(String[] args) throws IOException, RarException {
		if (args.length == 0) {
			System.out.print("Path: ");
			// String path = new java.util.Scanner(System.in).nextLine();

			String path = "F:\\code\\java\\ImgJudge\\01";

			System.out.println((new File(path).isDirectory() ? doDIR(path) : doRAR(path)));
		} else {
			for (String item : args)
				System.out.println((new File(item).isDirectory() ? doDIR(item) : doRAR(item)));
		}
		while (!RAR_LIST.isEmpty())
			System.out.println(doRAR(RAR_LIST.poll()));
	}
}
