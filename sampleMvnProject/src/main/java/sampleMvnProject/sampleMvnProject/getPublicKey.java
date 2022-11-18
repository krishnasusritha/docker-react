package sampleMvnProject.sampleMvnProject;

import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.Call;
import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.TlsVersion;

public class getPublicKey {

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		RSAPublicKey pkey = (RSAPublicKey) retrieveVerificationKeyFromUrl("6f9vp2YGhdzERFeTAEcf7VIwbrcdgILHTntvzhhPkQo");
		System.out.println(pkey);
	}
	
	public static PublicKey retrieveVerificationKeyFromUrl(String alias) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException{
		final String methodName = "retrieveVerificationKeyFromUrl(String)";
		PublicKey publicKey = null;
		Response clientResponse = null;
		List<ConnectionSpec> prots = new ArrayList<ConnectionSpec>();
		prots.add(new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS).tlsVersions(TlsVersion.TLS_1_1, TlsVersion.TLS_1_2, TlsVersion.TLS_1_3).build());
		OkHttpClient client = new OkHttpClient.Builder()
				.callTimeout(java.time.Duration.ofSeconds(1130000)).build();
		System.out.println(client.connectionSpecs().iterator().next().tlsVersions());
		HttpUrl.Builder urlBuilder = HttpUrl.parse("https://dev-906776.oktapreview.com/oauth2/ausebi0hs5liZf6FC0h7/v1/keys").newBuilder();
		String url = urlBuilder.build().toString();
		Request clientRequest = new Request.Builder().url(url).get().build();
		
		Call call = client.newCall(clientRequest);
		clientResponse = call.execute();
		
		String body =  clientResponse.body().string();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonResponse = mapper.readTree(body);
		for(JsonNode key : jsonResponse.get("keys")) {
			System.out.println(key.findValue("kid").asText());
			if("cWM7C0TRCy8dPVF-JHFgsFilw3fsMwq7vcW1wJZnsJY".equals(key.findValue("kid").asText())) {
				System.out.println("n : " +key.findValue("n").asText());
				byte[] nBytes = Base64.getUrlDecoder().decode(key.findValue("n").asText().getBytes());
				byte[] eBytes = Base64.getUrlDecoder().decode(key.findValue("e").asText().getBytes());

				BigInteger n = new BigInteger(1, nBytes);
				BigInteger e = new BigInteger(1, eBytes);

				RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n,e);
				KeyFactory keyFactory = KeyFactory.getInstance(jsonResponse.get("keys").get(0).get("kty").asText()); //kty will be "RSA"
				publicKey = keyFactory.generatePublic(publicKeySpec);
				break;
			}
		}
		
		//byte[] publicBytes = Base64.getDecoder().decode(key);
	//	X509EncodedKeySpec keySpec = new X509EncodedKeySpec(jsonResponse.get("keys").get(0).asText().getBytes(), "RSA");
	//	KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	//	publicKey = keyFactory.generatePublic(keySpec);
		
		return publicKey;
	}

}
