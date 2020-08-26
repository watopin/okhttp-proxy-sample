package gradle_sample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class Main {

	public static void main(String[] args) {
		
		apiCallTest();
		
	}
	
	public static void apiCallTest() {
		String apikey = "asdfasdfasdf";
		
		String endpoint = "https://sample.com";
		
		String apipath = "/api/v2/userInfo";
		
		OkHttpClient client = provideHttpClient("user", "password");
		Request req = new Request.Builder().url(endpoint + apipath + "?apiKey=" + apikey).build();
		
		try {
			Response res = client.newCall(req).execute();
			System.out.println(res.body().string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * create http client with proxy authentication.
	 * @param username username of proxy
	 * @param password password of proxy
	 * @return http client object
	 */
	public static OkHttpClient provideHttpClient(String username, String passwd) {
		Authenticator auth = new Authenticator() {
			
			@Override
			public Request authenticate(Route arg0, Response arg1) throws IOException {
				String cred = Credentials.basic(username, passwd);
				return arg1.request().newBuilder().addHeader("Proxy-Authorization", cred).build();
			}
		};
		
		Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("sample.proxy.co.jp", 8080));
		
		return new OkHttpClient().newBuilder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.proxy(p)
				.proxyAuthenticator(auth).build();
	}
}
