package com.jt;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Test;

/**
 * 用于计算字符串，字节数组或本地文件的MD5值
 * 然后将值以64位字节数组的形式或32位16进制数的方式输出
 * @author WYY
 *
 */
public class Md5Test {
	
	private static final int A_origin = 0x67452301;
	private static final int B_origin = 0xefcdab89;
	private static final int C_origin = 0x98badcfe;
	private static final int D_origin = 0x10325476;
	
	private static final int Normal = 1;
	private static final int NotEnough = 2;
	private static final int Full = 3;
	
	private int A = 0x67452301;
	private int B = 0xefcdab89;
	private int C = 0x98badcfe;
	private int D = 0x10325476;

	private static char[] hexs = { '0', '1', '2', '3', '4', '5', '6'
		, '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	private static int[] tis = new int[65];
	static {
		for (int i = 1; i < tis.length; i++) {
			long j = (long) (Math.sin(i) * mul(2L, 32));
			tis[i] = (int) Math.abs(j);
		}
	}
	
	/**
	 * 输入的数据字节长度
	 */
	private long length;
	
	private byte[] len_bytes;
	
	//循环次数
	private int N;
	
	private byte[] datas;
	
	private File file;
	
	private InputStream fis;
	
	private boolean isFile;
	
	private int dataType;
	
	
	public static void main(String[] args) throws Exception {
		File file = new File("c://[头文字D(国粤)]Initial.D.2005.BluRay.720p.x264.2Audio.AAC-iSCG.mp4");
		Md5Test md5 = new Md5Test();
//		md5.loadData(file);
		md5.loadData("abcde");
		Date d = new Date();
		System.out.println(md5.getMD5Str());
		System.out.println(new Date().getTime() - d.getTime());
		
	}
	
	/**
	 * 得到MD5字符串
	 * @return
	 */
	public String getMD5Str(){
		byte[] bytes = getMD5Bytes();
		String str = "";
		for(byte b: bytes){
			str += byteToHex(b);
		}
		return str;
	}
	
	/**
	 * 得到md5值的字节数组
	 * @return
	 */
	public byte[] getMD5Bytes(){
		fillData();
		calculate0();
		byte[] bytes = new byte[16];
		int k = 0;
		for (int i = 0; i < 4; i++) {
			bytes[k++] = (byte) (this.A >> (i * 8) & 0xff);
		}
		for (int i = 0; i < 4; i++) {
			bytes[k++] = (byte) (this.B >> (i * 8) & 0xff);
		}
		for (int i = 0; i < 4; i++) {
			bytes[k++] = (byte) (this.C >> (i * 8) & 0xff);
		}
		for (int i = 0; i < 4; i++) {
			bytes[k++] = (byte) (this.D >> (i * 8) & 0xff);
		}
		return bytes;
	}
	
	private void init() {
		this.A = A_origin;
		this.B = B_origin;
		this.C = C_origin;
		this.D = D_origin;
		this.length = 0;
		this.len_bytes = null;
		this.N = 0;
		this.datas = null;
		this.file = null;
		this.isFile = false;
		this.fis = null;
	}

	private int[] calculate0(){
		if(this.isFile){
			try {
//				this.fis = new FileInputStream(this.file);
				this.fis = new BufferedInputStream(
						new FileInputStream(this.file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		calEachGroup();
		return new int[]{A,B,C,D};
	}
	
	private void fillData() {
		if(this.isFile){
			
		}else{
			byte[] bytes = new byte[this.N * 64];
			int k = 0;
			for(byte b : this.datas){
				bytes[k++] = b;
			}
			if(this.dataType == Normal){
				bytes[k++] = -128;
				for (int i = (k % 64); i < 56; i++) {
					bytes[k++] = 0;
				}
			}else if(this.dataType == Full){
				bytes[k++] = 0;
				for (int i = 1; i < 56; i++) {
					bytes[k++] = 0;
				}
			}else{
				bytes[k++] = -128;
				for (int i = (k % 64); i < 64; i++) {
					bytes[k++] = 0;
				}
				for (int i = 0; i < 56; i++) {
					bytes[k++] = 0;
				}
			}
			for (int i = 0; i < 8; i++) {
				bytes[k++] = this.len_bytes[i];
			}
			this.datas = bytes;
		}
	}
	
	private void calEachGroup(){
		int[] M = new int[16];
		for (int i = 0; i < this.N; i++) {
			//加载下一轮数据
			if(this.isFile){
				getNextFileGroup(M,i);
			}else{
				getNextByteGroup(M,i);
			}
			//计算下一轮的数据
			calOneGroup(M);
		}
	}
	
	private void getNextFileGroup(int[] M, int time) {
		byte[] bytes = new byte[64];
		int k = -1;
		try {
			k = fis.read(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(k < 64 && k >= 56){
			bytes[k] = -128;
			for (int i = k + 1; i < bytes.length; i++) {
				bytes[i] = 0;
			}
		}else if(k == 64){
		}else if(k > 0){
			bytes[k] = -128;
			for (int i = k + 1; i < 56; i++) {
				bytes[i] = 0;
			}
			for (int i = 0; i < 8; i++) {
				bytes[i + 56] = this.len_bytes[i];
			}
		}else if(k == -1){
			if(dataType == Full){
				bytes[0] = -128;
			}else if(dataType == NotEnough){
				bytes[0] = 0;
			}
			for (int i = 1; i < 56; i++) {
				bytes[i] = 0;
			}
			for (int i = 0; i < 8; i++) {
				bytes[i + 56] = this.len_bytes[i];
			}
		}else{
			new Exception("运算异常！").printStackTrace();
		}
		for (int i = 0; i < 16; i++) {
			byte b1 = bytes[i * 4];
			byte b2 = bytes[i * 4 + 1];
			byte b3 = bytes[i * 4 + 2];
			byte b4 = bytes[i * 4 + 3];
			M[i] =  ((b4 & 0xff) << 24 ) |
					((b3 & 0xff) << 16 ) |
					((b2 & 0xff) << 8 ) |
					((b1 & 0xff));
		}
	}

	//得到字节的下一轮的数据
	private void getNextByteGroup(int[] M,int time) {
		for (int i = 0; i < 16; i++) {
			byte b1 = this.datas[time * 64 + i * 4];
			byte b2 = this.datas[time * 64 + i * 4 + 1];
			byte b3 = this.datas[time * 64 + i * 4 + 2];
			byte b4 = this.datas[time * 64 + i * 4 + 3];
			M[i] =  ((b4 & 0xff) << 24 ) |
					((b3 & 0xff) << 16 ) |
					((b2 & 0xff) << 8 ) |
					((b1 & 0xff));
		}
	}
	
	/**
	 * 加载一个字符串数据
	 * @param str
	 */
	public void loadData(String str){
		loadData(str.getBytes());
	}
	
	/**
	 * 加载一个字节数组
	 * @param bytes
	 */
	public void loadData(byte[] bytes){
		init();
		this.length = bytes.length;
		datas = bytes;
		this.isFile = false;
		initLengthData();
	}
	
	/**
	 * 加载一个本地文件
	 * @param file
	 */
	public void loadData(File file){
		init();
		this.length = file.length();
		this.file = file;
		this.isFile = true;
		initLengthData();
	}
	
	//计算总循环次数
	private void initLengthData() {
		int mod = (int) (this.length % 64);
		this.N = (int) (this.length / 64);
		//计算总循环次数并补全数据
		if(mod < 56){
			N += 1;
		}else if (mod >= 56){
			N += 2;
		}
		
		int check = (int) (this.length % 64);
		if(check == 0){
			this.dataType = Full;
		}else if(check >= 56){
			this.dataType = NotEnough;
		}else{
			this.dataType = Normal;
		}
		System.out.println(this.dataType);
		System.out.println(this.length);
		
		this.len_bytes = new byte[8];
		for (int i = 0; i < 8; i++) {
			this.len_bytes[i] = (byte)(this.length * 8 >> (8 * i) & 0xff);
		}
	}

	/**
	 * 将一个字节转换成16进制数字符串
	 * @param b
	 * @return
	 */
	public static String byteToHex(byte b) {
		char c = hexs[b & 0xf];
		char d = hexs[(b & 0xf0) >> 4];
		return "" + d + c;
	}

	/**
	 * 将一个int类型转换成16进制字符串并以字节序由低到高显示
	 * @param i
	 * @return
	 */
	public static String intToHex(int i) {
		byte b1 = (byte) (i & 0xff);
		byte b2 = (byte) (i >> 8 & 0xff);
		byte b3 = (byte) (i >> 16 & 0xff);
		byte b4 = (byte) (i >> 24 & 0xff);
		return byteToHex(b1) + byteToHex(b2)
			 + byteToHex(b3) + byteToHex(b4);
	}
	
	@Test
	public void test(){
		System.out.println(intToHex(0x78563412));
	}
	
	
	public void calOneGroup(int[] M) {
		int a = A;
		int b = B;
		int c = C;
		int d = D;

		a = ff(a, b, c, d, M[0], 7, 1);
		d = ff(d, a, b, c, M[1], 12, 2);
		c = ff(c, d, a, b, M[2], 17, 3);
		b = ff(b, c, d, a, M[3], 22, 4);
		a = ff(a, b, c, d, M[4], 7, 5);
		d = ff(d, a, b, c, M[5], 12, 6);
		c = ff(c, d, a, b, M[6], 17, 7);
		b = ff(b, c, d, a, M[7], 22, 8);
		a = ff(a, b, c, d, M[8], 7, 9);
		d = ff(d, a, b, c, M[9], 12, 10);
		c = ff(c, d, a, b, M[10], 17, 11);
		b = ff(b, c, d, a, M[11], 22, 12);
		a = ff(a, b, c, d, M[12], 7, 13);
		d = ff(d, a, b, c, M[13], 12, 14);
		c = ff(c, d, a, b, M[14], 17, 15);
		b = ff(b, c, d, a, M[15], 22, 16);

		a = gg(a, b, c, d, M[1], 5, 17);
		d = gg(d, a, b, c, M[6], 9, 18);
		c = gg(c, d, a, b, M[11], 14, 19);
		b = gg(b, c, d, a, M[0], 20, 20);
		a = gg(a, b, c, d, M[5], 5, 21);
		d = gg(d, a, b, c, M[10], 9, 22);
		c = gg(c, d, a, b, M[15], 14, 23);
		b = gg(b, c, d, a, M[4], 20, 24);
		a = gg(a, b, c, d, M[9], 5, 25);
		d = gg(d, a, b, c, M[14], 9, 26);
		c = gg(c, d, a, b, M[3], 14, 27);
		b = gg(b, c, d, a, M[8], 20, 28);
		a = gg(a, b, c, d, M[13], 5, 29);
		d = gg(d, a, b, c, M[2], 9, 30);
		c = gg(c, d, a, b, M[7], 14, 31);
		b = gg(b, c, d, a, M[12], 20, 32);

		a = hh(a, b, c, d, M[5], 4, 33);
		d = hh(d, a, b, c, M[8], 11, 34);
		c = hh(c, d, a, b, M[11], 16, 35);
		b = hh(b, c, d, a, M[14], 23, 36);
		a = hh(a, b, c, d, M[1], 4, 37);
		d = hh(d, a, b, c, M[4], 11, 38);
		c = hh(c, d, a, b, M[7], 16, 39);
		b = hh(b, c, d, a, M[10], 23, 40);
		a = hh(a, b, c, d, M[13], 4, 41);
		d = hh(d, a, b, c, M[0], 11, 42);
		c = hh(c, d, a, b, M[3], 16, 43);
		b = hh(b, c, d, a, M[6], 23, 44);
		a = hh(a, b, c, d, M[9], 4, 45);
		d = hh(d, a, b, c, M[12], 11, 46);
		c = hh(c, d, a, b, M[15], 16, 47);
		b = hh(b, c, d, a, M[2], 23, 48);

		a = ii(a, b, c, d, M[0], 6, 49);
		d = ii(d, a, b, c, M[7], 10, 50);
		c = ii(c, d, a, b, M[14], 15, 51);
		b = ii(b, c, d, a, M[5], 21, 52);
		a = ii(a, b, c, d, M[12], 6, 53);
		d = ii(d, a, b, c, M[3], 10, 54);
		c = ii(c, d, a, b, M[10], 15, 55);
		b = ii(b, c, d, a, M[1], 21, 56);
		a = ii(a, b, c, d, M[8], 6, 57);
		d = ii(d, a, b, c, M[15], 10, 58);
		c = ii(c, d, a, b, M[6], 15, 59);
		b = ii(b, c, d, a, M[13], 21, 60);
		a = ii(a, b, c, d, M[4], 6, 61);
		d = ii(d, a, b, c, M[11], 10, 62);
		c = ii(c, d, a, b, M[2], 15, 63);
		b = ii(b, c, d, a, M[9], 21, 64);

		A = plus(A, a);
		B = plus(B, b);
		C = plus(C, c);
		D = plus(D, d);
	}

	public static int plus(int x, int y) {
		return ((x & 0x7fffffff) + (y & 0x7fffffff)) 
				^ (x & 0x80000000) ^ (y & 0x80000000);
	}

	public static long mul(long x, int n) {
		if (n == 1) {
			return x;
		}
		return x * mul(x, n - 1);
	}

	/**
	 * 将正整数x循环左移n位 n超出正常位会输出异常
	 * 
	 * @param x
	 * @param n
	 * @return
	 */
	public static int cycleLeft(int x, int n) {
		int y = x >>> (32 - n);
		x = x << n;
		x = x | y;
		return x;
	}

	public static int f(int x, int y, int z) {
		return (x & y) | ((~x) & z);
	}

	public static int g(int x, int y, int z) {
		return (x & z) | (y & (~z));
	}

	public static int h(int x, int y, int z) {
		return x ^ y ^ z;
	}

	public static int i(int x, int y, int z) {
		return y ^ (x | (~z));
	}

	public static int ff(int a, int b, int c, int d, int data, int cyc, int ti) {
		return cycleLeft(a + f(b, c, d) + data + tis[ti], cyc) + b;
	}

	public static int gg(int a, int b, int c, int d, int data, int cyc, int ti) {
		return cycleLeft(a + g(b, c, d) + data + tis[ti], cyc) + b;
	}

	public static int hh(int a, int b, int c, int d, int data, int cyc, int ti) {
		return cycleLeft(a + h(b, c, d) + data + tis[ti], cyc) + b;
	}

	public static int ii(int a, int b, int c, int d, int data, int cyc, int ti) {
		return cycleLeft(a + i(b, c, d) + data + tis[ti], cyc) + b;
	}
}
