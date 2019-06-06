package com.yhml.bd.bd.compress;

import java.io.IOException;

/**
 * The Data Compress/UnCompress.<br>
 * <br>
 * env:JDK:1.7/CPU:4C/Compress Times：2000times<br>
 *
 * | Format | Size Before(byte) | Size After(byte) | Compress Expend(ms) | UnCompress Expend(ms) | MAX CPU(%) |
 * | bzip2  | 35984     | 8677  | 11591 | 2362  | 29.5  |
 * | gzip   | 35984     | 8804  | 2179  | 389   | 26.5  |
 * | deflate| 35984     | 9704  | 680   | 344   | 20.5  |
 * | lzo    | 35984     | 13069 | 581   | 230   | 22    |
 * | lz4    | 35984     | 16355 | 327   | 147   | 12.6  |
 * | snappy | 35984     | 13602 | 424   | 88    | 11    |
 *
 * @author lry
 */
public interface Compress {

	/**
	 * The Data compress.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	byte[] compress(byte[] data) throws IOException;

	/**
	 * The Data uncompress.
	 * 
	 * @param data
	 * @return
	 * @throws IOException
	 */
	byte[] uncompress(byte[] data) throws IOException;

}
