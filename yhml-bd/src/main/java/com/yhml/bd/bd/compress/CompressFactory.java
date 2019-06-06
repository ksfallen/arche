package com.yhml.bd.bd.compress;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.yhml.bd.bd.compress.support.SPI;


/**
 * The Data Compress Factory.
 *
 * @author lry
 */
public class CompressFactory {

	private static Map<String, Compress> compressMap = new HashMap<>();

	private CompressFactory() {}

    static {
		ServiceLoader<Compress> compresses = ServiceLoader.load(Compress.class);
		for (Compress compress : compresses) {
			SPI spi = compress.getClass().getAnnotation(SPI.class);
			if (spi != null) {
				String name = spi.value();
				if (compressMap.containsKey(name)) {
					throw new RuntimeException("The @SPI value(" + name
							+ ") repeat, for class(" + compress.getClass()
							+ ") and class(" + compressMap.get(name).getClass()
							+ ").");
				}

				compressMap.put(name, compress);
			}
		}
	}

	/**
	 * The getString compress @SPI value is {#name} extension.
	 *
	 * @param name
	 * @return
	 */
	public static Compress getExtension(String name) {
		return compressMap.get(name);
	}

}
