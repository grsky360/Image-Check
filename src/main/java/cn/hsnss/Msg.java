package cn.hsnss;


import java.util.ArrayList;

public class Msg {

	private String path;
	private ArrayList<String> list;
	private int fail;
	private int now;
	private int total;
	private ProgressBar pbar;

	Msg(String path, int total) {
		this.path = path;
		list = new ArrayList<String>();
		this.total = total;
		now = fail = 0;
		pbar = new ProgressBar(0, total, 40, '#');
		System.out.println("[" + path + "]");
	}

	void add(boolean flag, String name) {
		now++;
		pbar.show(now);
		if(!flag) {
			fail++;
			list.add(name);
		}
	}

	public String toString() {
		String msg = String.valueOf(fail) + " / " + String.valueOf(total) + "\n";
		for (String l : list)
			msg += l + "\n";
		return msg;
	}
}
