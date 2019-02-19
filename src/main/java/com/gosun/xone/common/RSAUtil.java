package com.gosun.xone.common;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class RSAUtil {

	private static Provider provider = new BouncyCastleProvider();
	private RSAPublicKey publicKey;
	private RSAPrivateKey privateKey;

	public RSAUtil(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}

	public String encrypt(String data) throws Exception {
		return Base64.encode(encrypt(this.privateKey, data.getBytes()));
	}

	public String dencrypt(String data) throws Exception {
		return new String(decrypt(this.publicKey, Base64.decode(data)));
	}

	public byte[] encrypt(byte[] data) throws Exception {
		return encrypt(this.privateKey, data);
	}

	public byte[] dencrypt(byte[] data) throws Exception {
		return decrypt(this.publicKey, data);
	}

	public String sign(String text) throws Exception {
		return sign(this.privateKey, text);
	}

	public boolean verify(String text, String sign) throws Exception {
		return verify(this.publicKey, text, sign);
	}

	public static String sign(PrivateKey privateKey, String text) throws Exception {
		Signature signature = Signature.getInstance("SHA1WithRSA", provider);
		signature.initSign(privateKey);
		signature.update(text.getBytes("utf8"));
		byte[] data = signature.sign();
		return Base64.encode(data);
	}

	public static boolean verify(PublicKey publicKey, String text, String sign) throws Exception {
		Signature signature = Signature.getInstance("SHA1WithRSA", provider);
		signature.initVerify(publicKey);
		signature.update(text.getBytes("utf8"));
		byte[] signed = Base64.decode(sign);
		if (signature.verify(signed)) {
			return true;
		}
		return false;
	}

	public static PrivateKey loadPrivateKey(String alias, String path, String password) throws Exception {
		FileInputStream stream = new FileInputStream(path);;
		return loadPrivateKey(alias, stream, password);
	}
	
	public static PrivateKey loadPrivateKey(String alias, FileInputStream stream, String password) throws Exception {
		try {
			KeyStore ks = KeyStore.getInstance("pkcs12");

			char[] storePwd = password.toCharArray();
			char[] keyPwd = password.toCharArray();
			ks.load(stream, storePwd);
			return (PrivateKey) ks.getKey(alias, keyPwd);
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static PublicKey loadPublicKey(String alias, String path, String password) throws Exception {
		FileInputStream stream = new FileInputStream(path);;
		return loadPublicKey(alias, stream, password);
	}

	public static PublicKey loadPublicKey(String alias, FileInputStream stream, String password) throws Exception {
		try {
			KeyStore ks = KeyStore.getInstance("pkcs12");
			char[] storePwd = password.toCharArray();
			ks.load(stream, storePwd);
			return ks.getCertificate(alias).getPublicKey();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", provider);
		} catch (NoSuchAlgorithmException ex) {
			throw ex;
		}
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw ex;
		}
	}

	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", provider);
		} catch (NoSuchAlgorithmException ex) {
			throw ex;
		}
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw ex;
		}
	}

	public static byte[] encrypt(Key key, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", provider);
			cipher.init(1, key);
			int blockSize = cipher.getBlockSize();
			int outputSize = cipher.getOutputSize(data.length);
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				}
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw e;
		}
	}

	public static byte[] decrypt(Key key, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", provider);
			cipher.init(2, key);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			throw e;
		}
	}

	public static String getPublicKeyString(RSAPublicKey key) throws Exception {
		String exponent = byte2hex(key.getPublicExponent().toByteArray());
		String modulus = byte2hex(key.getModulus().toByteArray());
		StringBuffer sb = new StringBuffer();
		sb.append(modulus).append(" ").append(exponent);
		return sb.toString();
	}

	public static String getPrivateKeyString(RSAPrivateKey key) throws Exception {
		String exponent = byte2hex(key.getPrivateExponent().toByteArray());
		String modulus = byte2hex(key.getModulus().toByteArray());
		StringBuffer sb = new StringBuffer();
		sb.append(modulus).append(" ").append(exponent);
		return sb.toString();
	}

	public static RSAPublicKey getPublicKey(String keyString) throws Exception {
		String[] part = keyString.split(" ");
		if (part.length != 2) {
			throw new Exception("��������������");
		}
		byte[] bytes = hex2byte(part[0]);
		BigInteger modulus = new BigInteger(bytes);
		bytes = hex2byte(part[1]);
		BigInteger publicExponent = new BigInteger(bytes);

		return generateRSAPublicKey(modulus.toByteArray(), publicExponent.toByteArray());
	}

	public static RSAPrivateKey getPrivateKey(String keyString) throws Exception {
		String[] part = keyString.split(" ");
		if (part.length != 2) {
			throw new Exception("��������������");
		}
		byte[] bytes = hex2byte(part[0]);
		BigInteger modulus = new BigInteger(bytes);
		bytes = hex2byte(part[1]);
		BigInteger privateExponent = new BigInteger(bytes);

		return generateRSAPrivateKey(modulus.toByteArray(), privateExponent.toByteArray());
	}

	public static String byte2hex(byte[] bytes) {
		StringBuffer retString = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			retString.append(Integer.toHexString(256 + (bytes[i] & 0xFF)).substring(1).toUpperCase());
		}
		return retString.toString();
	}

	public static String byte2hex(byte[] bytes, int index, int len) {
		StringBuffer retString = new StringBuffer();
		for (int i = index; i < len; i++) {
			retString.append(Integer.toHexString(256 + (bytes[i] & 0xFF)).substring(1).toUpperCase());
		}
		return retString.toString();
	}

	public static byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = ((byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16));
		}
		return bts;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.err.println("RSAUtil....");

		String alias = "certificatekey";
		String path = "c:/Users/dujunli/Documents/workspace-ggts/xgp/certs/clientkey.pfx";
		String password = "111111";

		String data = "aaaaaaaaaaaaaaaa";
		String sign = "";

		try {
			PrivateKey privateKey = RSAUtil.loadPrivateKey(alias, path, password);
			PublicKey publicKey = RSAUtil.loadPublicKey(alias, path, password);

			sign = RSAUtil.sign(privateKey, data);

			System.err.println("sign:" + sign);
			if (RSAUtil.verify(publicKey, data, sign)) {
				;
				System.err.println("success");
			} else {
				System.err.println("failed");
				System.err.println("sign" + sign);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
