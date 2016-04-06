import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.net.URL;
import java.io.*;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.*;

public class BouncyCastle
{
  public static void main(String[] args) throws Exception  {

    Security.addProvider(new BouncyCastleProvider());

    SSLContext context = SSLContext.getDefault();
    SSLSocketFactory sf = context.getSocketFactory();
    String[] cipherSuites = sf.getSupportedCipherSuites();

    System.out.println("CipherSuite:");
    for (String cipher : cipherSuites) {
      System.out.println("  " + cipher);
    }

    String httpsURL = args.length == 0 ? "httpbin.org" : args[0];
    URL myurl = new URL(httpsURL);
    HttpsURLConnection con = (HttpsURLConnection)myurl.openConnection();
    InputStream ins = con.getInputStream();
    InputStreamReader isr = new InputStreamReader(ins);
    BufferedReader in = new BufferedReader(isr);

    String inputLine;

    while ((inputLine = in.readLine()) != null)
    {
      System.out.println(inputLine);
    }

    in.close();
  }
}