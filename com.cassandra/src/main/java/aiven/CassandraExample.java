package aiven;

import com.datastax.driver.core.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CassandraExample {
	
	public static void main(String args[]) {
		try {
			

			String originalInput = "man";
			 byte[] val = originalInput.getBytes();
	final StringBuilder result = new StringBuilder();
    for(int i=0;i<val.length;i++) {
    	System.out.println(val[i]);
    }
    for(int j=0;j<val.length;j++) {
	for (int i=0; i<8; i++) {
		result.append((int)(val[j] >> (8-(i+1)) & 0x0001));
	}
	System.out.println( val[j]);
    }
	
	System.out.println( result.toString());

			CassandraExample.cassandraExample("cassandra-d824024-gautham1189-7df9.aivencloud.com", 18799, "avnadmin", "zv72oxov6nkprlsn", "C:\\ca.pem");
			//String originalInput = "man";
			System.out.println(originalInput.getBytes());
			String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
			System.out.println(encodedString);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    public static void cassandraExample(String host, int port, String username, String password, String caPath) throws Exception {
        SSLOptions sslOptions = loadCaCert(caPath);

        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                    .addContactPoint(host)
                    .withPort(port)
                    .withSSL(sslOptions)
                    .withAuthProvider(new PlainTextAuthProvider(username, password))
                    .build();
            Session session = cluster.connect();
            session.execute(
                    "CREATE KEYSPACE IF NOT EXISTS example_keyspace WITH REPLICATION = {'class': 'NetworkTopologyStrategy', 'aiven': 3}"
            );
            session.execute("USE example_keyspace");
            session.execute("CREATE TABLE IF NOT EXISTS example_java (id int PRIMARY KEY, message text)");
            session.execute("INSERT INTO example_java (id, message) VALUES (?, ?)", 123, "Hello from Java!");
            ResultSet rs = session.execute("SELECT id, message FROM example_java");
            for (Row row : rs) {
                System.out.println(String.format("Row: id = %d, message = %s", row.getInt("id"), row.getString("message")));
            }
        } finally {
            if (cluster != null) {
                cluster.close();
            }
        }
    }

    private static SSLOptions loadCaCert(String caCertPath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = null;
        X509Certificate caCert;
        try {
            fis = new FileInputStream(caCertPath);
            caCert = (X509Certificate) cf.generateCertificate(fis);
            
        } finally {
            if (fis != null) {
                fis.close();
            }
        }

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null);
        ks.setCertificateEntry("caCert", caCert);
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tmf.getTrustManagers(), null);
        return RemoteEndpointAwareJdkSSLOptions.builder().withSSLContext(sslContext).build();
    }
}