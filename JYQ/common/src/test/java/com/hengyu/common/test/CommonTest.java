package com.hengyu.common.test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import com.hengyu.common.util.EncryptionUtil;
import com.hengyu.common.util.RsaKeyHelper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonTest {
	@Test
	public void rsa() throws NoSuchAlgorithmException, IOException {
		new RsaKeyHelper().generateKey("src/main/resources/jwt/pub.key", "src/main/resources/jwt/pri.key", "123456");
	}

	@Test
	public void sha256StrJava() {
		log.info(EncryptionUtil.getSHA256StrJava("111222"));
	}
}
