package ro.geenie.network;

import com.google.code.gsonrmi.Parameter;
import com.google.code.gsonrmi.RpcError;
import com.google.code.gsonrmi.annotations.RMI;
import com.google.code.gsonrmi.serializer.ExceptionSerializer;
import com.google.code.gsonrmi.serializer.ParameterSerializer;
import com.google.code.gsonrmi.transport.Route;
import com.google.code.gsonrmi.transport.Transport;
import com.google.code.gsonrmi.transport.rmi.Call;
import com.google.code.gsonrmi.transport.rmi.RmiService;
import com.google.code.gsonrmi.transport.tcp.AndroidSecureTcpProxy;
import com.google.code.gsonrmi.transport.tcp.TcpProxy;
import com.google.code.gsonrmi.transport.tcp.TcpProxyFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;

public class GsonClient {

    private static GsonClient ourInstance;

    private Gson gson;

    private static KeyStore keyStore;

    private static Route to;
    private static URI from;
    private Transport transport;


    public static final int ANDROID_LISTENING_PORT = 30001;
    private static String SERVER_IP = "192.168.0.105";
    private static String SERVER_PORT = "30310";

    public static final String KEYSTORE_PASSWORD = "123456";


    private GsonClient() {}


    public static void registerKeystore(InputStream keystoreSource) {

        KeyStore localTrustStore;
        try {
            localTrustStore = KeyStore.getInstance("BKS");
            localTrustStore.load(keystoreSource, KEYSTORE_PASSWORD.toCharArray());
            keyStore = localTrustStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static GsonClient getInstance() {
        if (ourInstance == null){

            ourInstance = new GsonClient();
            ourInstance.gson = new GsonBuilder()
                    .registerTypeAdapter(Exception.class, new ExceptionSerializer())
                    .registerTypeAdapter(Parameter.class, new ParameterSerializer()).create();

            ourInstance.transport = new Transport();

            InetSocketAddress inetSocketAddress = new InetSocketAddress(ANDROID_LISTENING_PORT);

            List<InetSocketAddress> listeningAddresses =
                                                Arrays.asList(inetSocketAddress);


            TcpProxy tcpProxy;
            tcpProxy = TcpProxyFactory.reflectTcpProxy
                    (AndroidSecureTcpProxy.class,
                    listeningAddresses,
                    ourInstance.transport,
                    ourInstance.gson, keyStore);

            tcpProxy.start();

            RmiService rmiService = null;

            try {
                rmiService = new RmiService(ourInstance.transport, ourInstance.gson);
                rmiService.start();

                URI uri = null;
                try {
                    uri = new URI("rmi:service");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                Route target = new Route(uri);
                Call call = new Call(target, "register", "client", ourInstance);
                call.send(ourInstance.transport);

                to = new Route
                        (new URI("tcp://" + SERVER_IP + ":" + SERVER_PORT),
                                new URI("rmi:server"));

                from = new URI("rmi:client");

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        return ourInstance;
    }


//
//    public void contactCompensatField(String googleId, boolean state) {
//
//        Call basic = new Call(to, "setCompensatField", googleId, state);
//        Call response = basic.callback(from, "gotCompensatResponse");
//
//        response.send(transport);
//    }

    public void alertServer() {
        Call basic = new Call(to, "alert", "Salut");
        basic.send(transport);
    }


    @RMI
    public void serverResponded(String msg, RpcError rpcError) {
        System.out.println(msg);
    }

}

