package util;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;

public class TestCipher {
	public static void main(String[] args) {
		String msg = "Geo1rge6Geo1rge6Geo1rge6George";
		String encodeMsg = "";
		String decodeMsg = "";
		try {
			encodeMsg = CipherMsg.encryptMsg(msg);
			decodeMsg = CipherMsg.dencryptMsg(encodeMsg);
			System.out.println("原文："+msg+System.lineSeparator()+"加密後："+encodeMsg+System.lineSeparator()+"解密後："+decodeMsg);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (ShortBufferException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
