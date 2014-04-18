package edu.fudan.se.service.servlet.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;

public class IOUtil {

	public static byte[] getBundleFile(String path) {
		byte[] buffer = null;
		BufferedInputStream in = null;
		ByteArrayOutputStream bos = null;
		try {
			in = new BufferedInputStream(new FileInputStream(path));
			bos = new ByteArrayOutputStream(Parameter.BUFFER_SIZE);
			byte[] b = new byte[Parameter.BUFFER_SIZE];
			int n;
			while ((n = in.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			buffer = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(in);
			close(bos);
		}
		return buffer;
	}

	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
