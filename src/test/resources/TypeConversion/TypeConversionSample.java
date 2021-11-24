
public class TypeConversionSample {

	public TypeConversionSample() {

	}
	
	public void shortToByte() {
		short a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void shortToByteTransformed() {
		short a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void charToByte() {
		char a = '4';
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void charToByteTransformed() {
		char a = '4';
		byte b = (byte) Character.digit(a, 10);
		
		System.out.println(b);
	}
	
	public void charToShort() {
		char a = '4';
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void charToShortTransformed() {
		char a = '4';
		short b = (short) Character.digit(a, 10);
		
		System.out.println(b);
	}
	
	public void intToByte() {
		int a = 288;
		byte b;
		
		b = (byte) a;
		
		System.out.println(b);
	}
	
	public void intToByteTransformed() {
		int a = 288;
		byte b;
		
		b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void longToByte() {
		long a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void longToByteTransformed() {
		long a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void floatToByte() {
		float a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void floatToByteTransformed() {
		float a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void doubleToByte() {
		double a = 288;
		byte b = (byte) a;
		
		System.out.println(b);
	}
	
	public void doubleToByteTransformed() {
		double a = 288;
		byte b = (byte) (a % 256);
		
		System.out.println(b);
	}
	
	public void intToShort() {
		int a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void intToShortTransformed() {
		int a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void longToShort() {
		long a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void longToShortTransformed() {
		long a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void floatToShort() {
		float a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void floatToShortTransformed() {
		float a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void doubleToShort() {
		double a = 32768;
		short b = (short) a;
		
		System.out.println(b);
	}
	
	public void doubleToShortTransformed() {
		double a = 32768;
		short b = (short) (a % 65536);
		
		System.out.println(b);
	}
	
	public void longToInt() {
		long a = 2147483648L;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void longToIntTransformed() {
		long a = 2147483648L;
		int b = Math.toIntExact(a);
		
		System.out.println(a);
	}
	
	public void floatToInt() {
		float a = 1.99f;
		int b;
		
		b = (int) a;
		
		System.out.println(b);
	}
	
	public void floatToIntTransformed() {
		float a = 1.99f;
		int b;
		
		b = (int) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void doubleToInt() {
		double a = 1.99;
		int b = (int) a;
		
		System.out.println(b);
	}
	
	public void doubleToIntTransformed() {
		double a = 1.99;
		int b = (int) Math.floor(a);
		
		System.out.println(b);
	}
	
	public void floatToLong() {
		float a = 1.99f;
		long b = (long) a;
		
		System.out.println(b);
	}
	
	public void floatToLongTransformed() {
		float a = 1.99f;
		long b = (long) Math.floor(a);
		
		System.out.println(a);
	}
	
	public void doubleToLong() {
		double a = 1.99;
		long b;
		
		b = (long) a;
		
		System.out.println(b);
	}
	
	public void doubleToLongTransformed() {
		double a = 1.99;
		int b;
		
		b = Math.round(a);
		
		System.out.println(a);
	}
	
	public void shortToChar() {
		short a = 4;
		char b = (char) a;
		
		System.out.println(b);
	}
	
	public void shortToCharTransformed() {
		short a = 4;
		char b = Character.forDigit(a, 10);
		
		System.out.println(b);
	}
	
	public void intToChar() {
		int a = 4;
		char b; 
		
		b = (char) a;
		
		System.out.println(b);
	}
	
	public void intToCharTransformed() {
		int a = 4;
		char b;
		
		b = Character.forDigit(a, 10);
		
		System.out.println(b);
	}
	
	public void longToChar() {
		long a = 4;
		char b = (char) a;
		
		System.out.println(b);
	}
	
	public void longToCharTransformed() {
		long a = 4;
		char b = Character.forDigit((int) a, 10);
		
		System.out.println(b);
	}
	
	public char floatToChar(float a) {
		a = 4;
		char b = (char) a;
		
		return b;
	}
	
	public void floatToCharTransformed() {
		float a = 4;
		char b = Character.forDigit((int) a, 10);
		
		System.out.println(b);
	}
	
	public void doubleToChar() {
		double a = 4;
		char b = (char) a;
		
		System.out.println(b);
	}
	
	public void doubleToCharTransformed() {
		double a = 4;
		char b = Character.forDigit((int) a, 10);
		
		System.out.println(b);
	}
	
	public byte shortToByteMethod(short value) {
		System.out.println(value);
		return (byte) value;
	}
	
	public byte shortToByteMethodTransformed(short value) {
		System.out.println(value);
		return (byte) (value % 256);
	}
	
	public byte charToByteMethod(char value) {
		System.out.println(value);
		return (byte) value;
	}
	
	public byte charToByteMethodTransformed(char value) {
		System.out.println(value);
		return (byte) Character.digit(value, 10);
	}
	
	public short charToShortMethod(char value) {
		System.out.println(value);
		return (short) value;
	}
	
	public short charToShortMethodTransformed(char value) {
		System.out.println(value);
		return (short) Character.digit(value, 10);
	}
	
	public byte intToByteMethod(int value) {
		System.out.println(value);
		return (byte) value;
	}
	
	public byte intToByteMethodTransformed(int value) {
		System.out.println(value);
		return (byte) (value % 256);
	}
	
	public byte longToByteMethod(long value) {
		System.out.println(value);
		return (byte) value;
	}
	
	public byte longToByteMethodTransformed(long value) {
		System.out.println(value);
		return (byte) (value % 256);
	}
	
	public byte floatToByteMethod(float value) {
		System.out.println(value);
		return (byte) value;
	}
	
	public byte floatToByteMethodTransformed(float value) {
		System.out.println(value);
		return (byte) (value % 256);
	}
	
	public byte doubleToByteMethod(double value) {
		System.out.println(value);
		return (byte) value;
	}
	
	public byte doubleToByteMethodTransformed(double value) {
		System.out.println(value);
		return (byte) (value % 256);
	}
	
	public short intToShortMethod(int value) {
		System.out.println(value);
		return (short) value;
	}
	
	public short intToShortMethodTransformed(int value) {
		System.out.println(value);
		return (short) (value % 65536);
	}
	
	public short longToShortMethod(long value) {
		System.out.println(value);
		return (short) value;
	}
	
	public short longToShortMethodTransformed(long value) {
		System.out.println(value);
		return (short) (value % 65536);
	}
	
	public short doubleToShortMethod(double value) {
		System.out.println(value);
		return (short) value;
	}
	
	public short doubleToShortMethodTransformed(double value) {
		System.out.println(value);
		return (short) (value % 65536);
	}
	
	public int longToIntMethod(long value) {
		System.out.println(value);
		return (int) value;
	}
	
	public int longToIntMethodTransformed(long value) {
		System.out.println(value);
		return Math.toIntExact(a);
	}
	
	public int floatToIntMethod(float value) {
		System.out.println(value);
		return (int) value;
	}
	
	public int floatToIntMethodTransformed(float value) {
		System.out.println(value);
		return (int) Math.floor(value);
	}
	
	public int doubleToIntMethod(double value) {
		System.out.println(value);
		return (int) value;
	}
	
	public int doubleToIntMethodTransformed(double value) {
		System.out.println(value);
		return (int) Math.floor(value);
	}
	
	public long floatToLongMethod(float value) {
		System.out.println(value);
		return (long) value;
	}
	
	public long floatToLongMethodTransformed(float value) {
		System.out.println(value);
		return (long) Math.floor(value);
	}
	
	public long doubleToLongMethod(double value) {
		System.out.println(value);
		return (long) value;
	}
	
	public long doubleToLongMethodTransformed(double value) {
		System.out.println(value);
		return Math.round(value);
	}
	
	public char shortToCharMethod(short value) {
		System.out.println(value);
		return (char) value;
	}
	
	public char shortToCharMethodTrasnformed(short value) {
		System.out.println(value);
		return Character.forDigit((int) value, 10);
	}
	
	public static char intToCharMethod(int value) {
		System.out.println(value);
		return (char) value;
	}
	
	public static char intToCharMethodTransformed(final int value) {
		System.out.println(value);
		return Character.forDigit(value, 10);
	}
	
	public char longToCharMethod(long value) {
		System.out.println(value);
		return (char) value;
	}
	
	public char longToCharMethodTransformed(long value) {
		System.out.println(value);
		return Character.forDigit((int) value, 10);
	}
	
	public char floatToCharMethod(float value) {
		System.out.println(value);
		return (char) value;
	}
	
	public char floatToCharMethodTrasnformed(float value) {
		System.out.println(value);
		return Character.forDigit((int) value, 10);
	}
	
	public char doubleToCharMethod(double value) {
		System.out.println(value);
		return (char) value;
	}
	
	public char doubleToCharMethodTransformed(double value) {
		System.out.println(value);
		return Character.forDigit((int) value, 10);
	}

}
