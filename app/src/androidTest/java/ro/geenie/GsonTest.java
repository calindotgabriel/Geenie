package ro.geenie;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.code.gsonrmi.Parameter;
import com.google.code.gsonrmi.RpcError;
import com.google.code.gsonrmi.annotations.RMI;
import com.google.code.gsonrmi.serializer.ExceptionSerializer;
import com.google.code.gsonrmi.serializer.ParameterSerializer;
import com.google.code.gsonrmi.transport.Route;
import com.google.code.gsonrmi.transport.Transport;
import com.google.code.gsonrmi.transport.rmi.Call;
import com.google.code.gsonrmi.transport.rmi.RmiService;
import com.google.code.gsonrmi.transport.tcp.TcpProxy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import ro.geenie.network.GsonClient;

/**
 * Created by motan on 09.02.2015.
 */
public class GsonTest extends ApplicationTestCase<Application> {

    public GsonTest() {
        super(Application.class);
    }

    public void testContactServer() throws IOException, URISyntaxException{
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Exception.class, new ExceptionSerializer())
                .registerTypeAdapter(Parameter.class, new ParameterSerializer())
                .create();
        Transport t = new Transport();
        new TcpProxy(Arrays.asList(new InetSocketAddress(30101)), t, gson).start();
        new RmiService(t, gson).start();

        //register an object for receiving return values
        new Call(new Route(new URI("rmi:service")), "register", "myObject", new GsonTest()).send(t);

        //invoke the remote object
        new Call(new Route(new URI("tcp://192.168.0.105:30100"), new URI("rmi:herObject")), "someMethod", "Jack")
                .callback(new URI("rmi:myObject"), "returnValueMethod")
                .send(t);
    }


}
