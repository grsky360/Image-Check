package cn.hsnss;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FileType {

	private static Map<String, String> FILE_TYPE = new HashMap<String, String>();
	private static Map<String, String[]> FOOT_CODE = new HashMap<String, String[]>();

	static {
		FILE_TYPE.put("ffd8ff", "jpg");
		FOOT_CODE.put("jpg", new String[] {"ffd9", "0d0a", "0d0a00"});
		FILE_TYPE.put("89504e47","png");
		FOOT_CODE.put("png", new String[] {"png", "ae426082", "0d0a", "0d0a00"});
		FILE_TYPE.put("47494638", "gif");
		FOOT_CODE.put("gif", new String[] {"gif", "003b", "0d0a", "0d0a00"});
	}

	static String byteToHexString(byte[] b) {
		StringBuilder str = new StringBuilder();
		if(b == null || b.length <= 0)
			return null;
		for (byte i : b) {
			String hv = Integer.toHexString(i & 0xFF).toLowerCase();
			if (hv.length() < 2)
				str.append(0);
			str.append(hv);
		}
		return str.toString();
	}

	static String[] getFileCode(InputStream is, long size) {
		String head = null, foot = null;
		if (is == null)
			return null;
		byte[] b = new byte[4];
		try {
			is.read(b);
			head = FileType.byteToHexString(b);
			is.skip(size - 8);
			is.read(b);
			foot = FileType.byteToHexString(b);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[]{head, foot};
	}

	static boolean judge(InputStream is, long size) {
		String[] code = FileType.getFileCode(is, size);
		String[] footcode = null;
		for (String key : FILE_TYPE.keySet()) {
			if (code[0].startsWith(key)) {
				footcode = FOOT_CODE.get(FILE_TYPE.get(key));
				break;
			}
		}
		for (String foot : footcode) {
			if (code[1].endsWith(foot))
				return true;
		}
		return false;
	}
}
