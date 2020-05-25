// package com.yhml.bd.bd.compress.impl;
//
// import com.yhml.bd.bd.compress.Compress;
// import com.yhml.bd.bd.compress.support.SPI;
//
// import java.io.ByteArrayInputStream;
// import java.io.ByteArrayOutputStream;
// import java.io.IOException;
//
//
// import net.jpountz.lz4.*;
//
// /**
//  * The Data Compression Based on lz4.
//  *
//  * @author lry
//  */
// @SPI("lz4")
// public class Lz4Compress implements Compress {
//
// 	@Override
// 	public byte[] compress(byte[] data) throws IOException {
// 		LZ4Factory factory = LZ4Factory.fastestInstance();
// 		ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
// 		LZ4Compressor compressor = factory.fastCompressor();
// 		LZ4BlockOutputStream compressedOutput = new LZ4BlockOutputStream(byteOutput, 2048, compressor);
// 		compressedOutput.write(data);
// 		compressedOutput.close();
//
// 		return byteOutput.toByteArray();
// 	}
//
// 	@Override
// 	public byte[] uncompress(byte[] data) throws IOException {
// 		LZ4Factory factory = LZ4Factory.fastestInstance();
// 		ByteArrayOutputStream baos = new ByteArrayOutputStream();
// 		LZ4FastDecompressor decompresser = factory.fastDecompressor();
// 		LZ4BlockInputStream lzis = new LZ4BlockInputStream(new ByteArrayInputStream(data), decompresser);
//
// 		int count;
// 		byte[] buffer = new byte[2048];
// 		while ((count = lzis.read(buffer)) != -1) {
// 			baos.write(buffer, 0, count);
// 		}
// 		lzis.close();
//
// 		return baos.toByteArray();
// 	}
//
// }
