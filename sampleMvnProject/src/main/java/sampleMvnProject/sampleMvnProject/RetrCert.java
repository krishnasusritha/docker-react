package sampleMvnProject.sampleMvnProject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.httpclient.HttpVersion;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

public class RetrCert {
		public static void main(String[] args) throws Exception {
			
			X509Certificate certificates = loadCertificatesFromFirebaseUrl("http://localhost:8888/ssooauth2testapp/gen-cert", "rsacert");

			System.out.println("Certificate map" + certificates.getPublicKey());
		}
		private static X509Certificate loadCertificatesFromFirebaseUrl(String externalCertUrl, String alias) throws IOException {
			if (externalCertUrl == null) {
				return null;
			}

			Response response = null;
			//File f = new File("C:\\PassportGitCheckout\\passport-thinclient\\ssoOAuth2TestApp\\src\\main\\resources\\rsakey.pem");
			//FileInputStream in=new FileInputStream(f);
			try {
//				List<Protocol> prots = new ArrayList<Protocol>();
//				prots.add(Protocol.HTTP_2);
//				prots.add(Protocol.HTTP_1_1);
//				OkHttpClient client = new OkHttpClient.Builder().protocols(prots).callTimeout(java.time.Duration.ofSeconds(1130000)).build();
//				HttpUrl.Builder urlBuilder = HttpUrl.parse(externalCertUrl).newBuilder();
//				String url = urlBuilder.setQueryParameter("alias", alias).build().toString();
//				Request request = new Request.Builder().url(url).get().build();
//				Call call = client.newCall(request);
//				response = call.execute();
//				String respHeader =response.header("certValue");
//				System.out.println("From Header " +respHeader);
//				String respBody = response.body().string();
//				System.out.println("From Body " + respBody.replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "").replace("\n", ""));
//				byte[] decoded = Base64.getDecoder()
//						.decode(respBody.replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "").replace("\n", ""));
//				return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));

				HttpClientBuilder builder = HttpClients.custom();
				builder.setConnectionTimeToLive(10000L, TimeUnit.MILLISECONDS);
				CloseableHttpClient httpClient = builder.build();
//				httpClient.getParams().setParameter("http.protocol.version", HttpVersion.HTTP_1_1);
//				httpClient.getParams().setParameter("http.protocol.cookie-policy", org.apache.commons.httpclient.cookie.CookiePolicy.BROWSER_COMPATIBILITY);
//				httpClient.getParams().setIntParameter("http.protocol.cookie-policy", 30000);
//				HttpURL httpUrl = new HttpURL(url);
//				httpUrl.setQuery("alias", alias);
				HttpGet httpGet = new HttpGet(externalCertUrl+"?alias="+ alias);
				httpGet.addHeader("Access-Control-Allow-Origin", "*");
				HttpResponse httpResponse = httpClient.execute(httpGet);
				//String respBody = new String(httpResponse.getEntity().getContent().readAllBytes());
				InputStream stream = httpResponse.getEntity().getContent();
				byte[] bArray = new byte[stream.available()];
				stream.read(bArray);
				String respBody = new String(bArray);
				byte[] decoded = Base64.getDecoder()
						.decode(respBody.replace("-----BEGIN CERTIFICATE-----", "").replace("-----END CERTIFICATE-----", "").replace("\n", ""));
				return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));

			} catch (Exception e) {
				System.out.println("Error processing certs at path: {}" + e);
				e.printStackTrace();
			} finally {
				if (response != null) {
					response.close();
				}
				   // in.close();
			}

			return null;
		}
	}

