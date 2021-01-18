package util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.utils.Utils;
import org.springframework.util.Base64Utils;

/* ref:https://www.cnblogs.com/dingmy/p/9467429.html */

public class CipherMsg {
	/* 256bit Key(32) */
	static final SecretKeySpec sKey = new SecretKeySpec(getUTF8Bytes("509152814454755875393931580903364056"), "AES");
	/* 128bit(16) */
	static final IvParameterSpec ips = new IvParameterSpec(getUTF8Bytes("1031251305590125"));
	/* 加密模式 */
	static final String transform = "AES/CBC/PKCS5Padding";
	
	/* String->String byte[](UTF-8) */
	private static byte[] getUTF8Bytes(String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }
	
	/* ByteBuffer->String(UTF-8) */
	private static String toBeString(ByteBuffer buffer) {
        /* duplicate()->用既有buffer的資料造一個新buffer */
		final ByteBuffer copy = buffer.duplicate();
		/* remaining()->回傳當中的元素個數 */
        final byte[] bytes = new byte[copy.remaining()];
        copy.get(bytes);
        /* 字串編碼回UTF-8後回傳 */
        return new String(bytes, StandardCharsets.UTF_8);
    }
	
	/* 加密 */
	public static String encryptMsg(String message) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
		Properties properties = new Properties();
		/* 輸出用buffer */
		final ByteBuffer outputBuffer;
		/* buffer大小 */
		final int bufferSize = 1024;
		final int updateBytes;
        final int finalBytes;
        try (CryptoCipher encipher = Utils.getCipherInstance(transform, properties)) {
        	ByteBuffer inputBuffer = ByteBuffer.allocateDirect(bufferSize);
        	outputBuffer = ByteBuffer.allocateDirect(bufferSize);
        	/* 放入欲加密的內容 */
        	inputBuffer.put(getUTF8Bytes(message));
        	/* 準備好做加密 */
        	inputBuffer.flip(); 
        	
        	/* 開始以下列參數執行加密ENCRYPT_MODE,sKey and ips */
            encipher.init(Cipher.ENCRYPT_MODE, sKey, ips);
            /* 中間步驟 */
            updateBytes = encipher.update(inputBuffer, outputBuffer);
            /* 結束 */
            finalBytes = encipher.doFinal(inputBuffer, outputBuffer);
        } 
        /* 準備好做解密 */
        outputBuffer.flip();
        byte[] encodedResult = new byte[updateBytes + finalBytes];
        outputBuffer.duplicate().get(encodedResult);
        /* 使用Base64編碼 */
        String encodedMessage = Base64Utils.encodeToString(encodedResult);
		return encodedMessage;
	}
	
	/* 解密 */
	public static String dencryptMsg(String encodedMessage) throws IOException, InvalidAlgorithmParameterException, InvalidKeyException, ShortBufferException, BadPaddingException, IllegalBlockSizeException {
		Properties properties = new Properties();
		final ByteBuffer outputBuffer;
        final int bufferSize = 1024;
        ByteBuffer decodedMessage = ByteBuffer.allocateDirect(bufferSize);
        try (CryptoCipher decipher = Utils.getCipherInstance(transform, properties)) {
        	/* 開始以下列參數執行解密DECRYPT_MODE,sKey and ips */
            decipher.init(Cipher.DECRYPT_MODE, sKey, ips);
            outputBuffer = ByteBuffer.allocateDirect(bufferSize);
            /* 將密文以Base64解碼並切換成String byte[] */
            outputBuffer.put(Base64Utils.decode(getUTF8Bytes(encodedMessage)));
            outputBuffer.flip();
            /* 中間步驟 */
            decipher.update(outputBuffer, decodedMessage);
            /* 結束 */
            decipher.doFinal(outputBuffer, decodedMessage);
            /* 準備好可用 */
            decodedMessage.flip();
        }
        /* 將ByteBuffer轉回String */
		return toBeString(decodedMessage);
	}
}
