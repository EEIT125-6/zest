package util;

public class TestCipher {
	public static void main(String[] args) {
//		String msg = "Geo1rge6Geo1rge6Geo1rge6George";
		String msg = "TestUser1256";
		String encodeMsg = "";
		String decodeMsg = "";
		encodeMsg = CipherMsg.encryptMsg(msg);
		decodeMsg = CipherMsg.dencryptMsg(encodeMsg);
		System.out.println("原文："+msg+System.lineSeparator()+"加密後："+encodeMsg+System.lineSeparator()+"解密後："+decodeMsg);

	}
}
