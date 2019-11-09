package de.profoethker.backendmodul.cybersecurity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class Hash512 {

	private final static String SHA512 = "SHA-512";

	private static final String BOUNCY_PROVIDER = "BC";

	public static String encryptData(String data) throws NoSuchAlgorithmException, NoSuchProviderException {
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest mda = MessageDigest.getInstance(SHA512, BOUNCY_PROVIDER);
		byte[] digesta = mda.digest(data.getBytes());

		String hash = Hex.encode(digesta).toString();
		return hash;
	}
}
