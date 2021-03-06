import java.net.URL;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class JavaNetSsl
{

  public static void setupSSLContext() throws Exception {
    KeyStore ts = KeyStore.getInstance("JKS");

    ts.load(new FileInputStream(".jdk/jre/lib/security/cacerts"), null);

    TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
    tmf.init(ts);

    SSLContext sslCtx = SSLContext.getInstance("TLSv1.2");

    sslCtx.init(null, tmf.getTrustManagers(), new SecureRandom());

    SSLContext.setDefault(sslCtx);
  }

  public static void main(String[] args) throws Exception  {
    setupSSLContext();

    String httpsURL = args.length == 0 ? "httpbin.org" : args[0];

    URL myurl = new URL(httpsURL);
    HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
    InputStream ins = con.getInputStream();
    InputStreamReader isr = new InputStreamReader(ins);
    BufferedReader in = new BufferedReader(isr);

    String inputLine;

    String html = "";
    while ((inputLine = in.readLine()) != null)
    {
      //System.out.println(inputLine);
      html += inputLine;
    }

    assert html.contains("</html>");

    in.close();

    System.out.println("success: " + html.substring(0, 50));
  }
}