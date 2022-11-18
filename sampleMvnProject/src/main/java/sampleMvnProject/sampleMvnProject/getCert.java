package sampleMvnProject.sampleMvnProject;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.X509Certificate;

public class getCert {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		X509Certificate cert = getX509CertFromKeyStore("rsacert");
		PublicKey publicKey = cert.getPublicKey();
		System.out.println(publicKey);
	}
	static X509Certificate getX509CertFromKeyStore(String alias) throws Exception {
			String absolutePath = FileSystems.getDefault().getPath("C:/test/keys/oauthkeystore").normalize().toAbsolutePath().toString();
			String decryptedPassword = "changeit";

			 KeyStore keystore =  KeyStore.getInstance( "JKS");
			 InputStream input = new FileInputStream(absolutePath);
			 keystore.load(input, decryptedPassword.toCharArray());
			X509Certificate cert = (X509Certificate) keystore.getCertificate(alias);

			return cert;
	}	

}
