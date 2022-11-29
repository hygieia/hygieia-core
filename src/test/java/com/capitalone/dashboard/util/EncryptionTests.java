package com.capitalone.dashboard.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EncryptionTests {

	private final String THING_TO_BE_ENCRYPTED = "AKIAJ24MI4VLOIR72NVA";
	private final String A_GOOD_STRING_KEY = "5XBoZ7li2W5wzhOULEqtQzdkufjsVFs4";
	private final String A_LONG_KEY = "0ED1C7B771C9BBAB2583C364AFE8FB0C2F23A6FC8157EE3601ABB53D9CEA9893";
	private final String A_SHORT_KEY = "F55CC56E8DB6056EB4085263";
	private final String A_BAD_STRING_KEY = "c/t/nuBFwTgvB+lwzS/q5W0ZkQhhxCB1";
	private static final String ALGO = "AES";
	private static final SecretKey GOOD_KEY = getKey();
	private static final SecretKey BAD_KEY = getKey();

	private static SecretKey getKey() {
		SecretKey key = null;
		try {
			key = KeyGenerator.getInstance(ALGO).generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
//		String stringKey = Base64.encodeBase64String(key.getEncoded());
		return key;
	}


	@Test
	public void testGetStringKey() {
		String key = null;
		try {
			key = Encryption.getStringKey();
		} catch (EncryptionException e) {
			e.printStackTrace();
		}
		assertNotEquals(null, key);
		assertNotEquals("", key);
	}

	@Test
	public void testGetSecretKey() {
		SecretKey key = null;
		try {
			key = Encryption.getSecretKey();
		} catch (EncryptionException e) {
			e.printStackTrace();
		}
		assertNotEquals(null, key);
		assertNotEquals(0, key.getEncoded().length);
	}

	@Test
	public void testEncryptDecryptString() {
		String encryptedString = null;
		String decryptedString = null;
		try {
			encryptedString = Encryption.encryptString(THING_TO_BE_ENCRYPTED,
					GOOD_KEY);
			decryptedString = Encryption.decryptString(encryptedString,
					GOOD_KEY);
		} catch (EncryptionException e) {
			e.printStackTrace();
		}
		assertEquals(THING_TO_BE_ENCRYPTED, decryptedString);
	}

	@Test
	public void testDecryptionWithBadKey() throws Exception {
		String encryptedString = Encryption.encryptString(THING_TO_BE_ENCRYPTED,
				GOOD_KEY);
		@SuppressWarnings("unused")
		
		EncryptionException thrown = assertThrows(EncryptionException.class, () -> {
			String decryptedString = Encryption.decryptString(encryptedString, BAD_KEY);
		});
		assertEquals("Cannot decrypt this message\nGiven final block not properly padded. Such issues can arise if a bad key is used during decryption.",thrown.getMessage());
	}


	@Test
	public void testDecryptNullString() throws Exception {
		EncryptionException thrown = assertThrows(EncryptionException.class, () -> {
			@SuppressWarnings("unused")
			String decryptedString = Encryption.decryptString(null, GOOD_KEY);
		});
		assertEquals("Cannot decrypt this message\nNull input buffer",thrown.getMessage());
	}

	@Test
	public void testEncryptEmptyString() throws Exception {
		String encryptedString = Encryption.encryptString("", GOOD_KEY);
		String decryptedString = Encryption.decryptString(encryptedString, GOOD_KEY);
		assertEquals("", decryptedString);
	}

	@Test
	public void testDecryptEmptyString() throws Exception {
		String decryptedString = Encryption.decryptString("", GOOD_KEY);
		assertEquals("", decryptedString);
	}

	@Test
	public void testEncryptString() {
		String encryptedString = null;
		String decryptedString = null;
		try {
			encryptedString = Encryption.encryptString(THING_TO_BE_ENCRYPTED,
					A_GOOD_STRING_KEY);
			decryptedString = Encryption.decryptString(encryptedString,
					A_GOOD_STRING_KEY);
		} catch (EncryptionException e) {
			e.printStackTrace();
		}
		assertEquals(THING_TO_BE_ENCRYPTED, decryptedString);
	}

	@Test
	public void testDecryptionWithBadStringKey() throws Exception {
		String encryptedString = Encryption.encryptString(THING_TO_BE_ENCRYPTED,
				A_GOOD_STRING_KEY);
		EncryptionException thrown = assertThrows(EncryptionException.class, () -> {
			@SuppressWarnings("unused")
			String decryptedString = Encryption.decryptString(encryptedString, A_BAD_STRING_KEY);
		});
		assertEquals("Cannot decrypt this message\nGiven final block not properly padded. Such issues can arise if a bad key is used during decryption.",thrown.getMessage());

	}

	@Test
	public void testShortKeyLength() throws Exception{
		
		EncryptionException thrown = assertThrows(EncryptionException.class, () -> {
			@SuppressWarnings("unused")
			String encryptedString = Encryption.encryptString(THING_TO_BE_ENCRYPTED,
					A_SHORT_KEY);
		});
		assertEquals("Cannot encrypt this message\nInvalid AES key length: 18 bytes",thrown.getMessage());
	}


	@Test
	public void testLongKeyLength() throws Exception{
		EncryptionException thrown = assertThrows(EncryptionException.class, () -> {
			@SuppressWarnings("unused")
			String encryptedString = Encryption.encryptString(THING_TO_BE_ENCRYPTED,
					A_LONG_KEY);
		});
		assertEquals("Cannot encrypt this message\nInvalid AES key length: 48 bytes",thrown.getMessage());
	}
}
