package com.yhml.bd.bd.compress.impl;

import java.io.IOException;

import org.xerial.snappy.Snappy;

import com.yhml.bd.bd.compress.Compress;
import com.yhml.bd.bd.compress.support.SPI;


/**
 * The Data Compression Based on snappy.
 * 
 * @author lry
 */
@SPI("snappy")
public class SnappyCompress implements Compress {

	@Override
	public byte[] compress(byte[] data) throws IOException {
		return Snappy.compress(data);
	}

	@Override
	public byte[] uncompress(byte[] data) throws IOException {
		return Snappy.uncompress(data);
	}

}
